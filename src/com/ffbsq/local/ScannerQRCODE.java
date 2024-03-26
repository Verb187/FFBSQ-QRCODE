package com.ffbsq.local;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

public class ScannerQRCODE extends JFrame {
	
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JPanel contentPane;
    private CameraPanel cameraPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ScannerQRCODE frame = new ScannerQRCODE();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public ScannerQRCODE() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        cameraPanel = new CameraPanel();
        cameraPanel.setBounds(0, 0, 640, 480);
        contentPane.add(cameraPanel);
    }

    /**
     * Panel to display camera feed.
     */
    class CameraPanel extends JPanel {
        private VideoCapture camera;
        private BufferedImage image;

        public CameraPanel() {
            camera = new VideoCapture(0); // Access the camera. Number 0 indicates the first available camera.

            if (!camera.isOpened()) {
                System.out.println("Cannot access camera!");
                return;
            }

            setPreferredSize(new Dimension(640, 480));

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Mat frame = new Mat();
                        camera.read(frame); // Capture an image from the camera

                        // Convert OpenCV Mat to BufferedImage
                        MatOfByte matOfByte = new MatOfByte();
                        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
                        Imgcodecs.imencode(".jpg", frame, matOfByte);
                        byte[] byteArray = matOfByte.toArray();
                        try {
                            image = ImageIO.read(new ByteArrayInputStream(byteArray));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // Read QR code
                        String qrCodeText = readQRCode(image);
                        if (qrCodeText != null) {
                            System.out.println("QR code content: " + qrCodeText);
                            break; // Exit the loop once QR code is found
                        }

                        repaint(); // Repaint the panel to display the new frame
                    }
                }
            });
            thread.start();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        }

        private String readQRCode(BufferedImage image) {
            try {
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

                Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
                hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

                Result result = new MultiFormatReader().decode(bitmap, hints);

                return result.getText();
            } catch (NotFoundException e) {
                return null; // No QR code found in the image
            }
        }
    }
}
