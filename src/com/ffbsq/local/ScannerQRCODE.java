package com.ffbsq.local;


import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
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
    private JPanel panelCamera;
    private CameraPanel cameraPanel;

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

    public ScannerQRCODE() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 640, 480);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        JButton btnScanQR = new JButton("Scanner un QRCODE");
        btnScanQR.setBounds(10, 10, 136, 38);
        contentPane.add(btnScanQR);
        btnScanQR.addActionListener(e -> {
            cameraPanel.startCamera();
        });

        panelCamera = new JPanel();
        panelCamera.setBounds(10, 64, 300, 300);
        contentPane.add(panelCamera);
        panelCamera.setLayout(null);

        cameraPanel = new CameraPanel();
        cameraPanel.setBounds(0, 0, 300, 300);
        panelCamera.add(cameraPanel);
    }

    class CameraPanel extends JPanel {
        private VideoCapture camera;
        private BufferedImage image;

        public CameraPanel() {
            setPreferredSize(new Dimension(300, 300));
        }

        public void startCamera() {
            camera = new VideoCapture(0); 

            if (!camera.isOpened()) {
                System.out.println("Cannot access camera!");
                return;
            }

            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        Mat frame = new Mat();
                        camera.read(frame); 

                        MatOfByte matOfByte = new MatOfByte();
                        Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2RGB);
                        Imgcodecs.imencode(".jpg", frame, matOfByte);
                        byte[] byteArray = matOfByte.toArray();
                        try {
                            image = ImageIO.read(new ByteArrayInputStream(byteArray));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String qrCodeText = readQRCode(image);
                        if (qrCodeText != null) {
                            System.out.println("QR code content: " + qrCodeText);
                            break; 
                        }

                        repaint(); 
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
                return null; 
            }
        }
    }
}
