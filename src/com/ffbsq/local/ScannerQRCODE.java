package com.ffbsq.local;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javax.imageio.ImageIO;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class ScannerQRCODE extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JPanel contentPane;
    private JPanel panelCamera;
    private CameraPanel cameraPanel;
    private JTextField textNumSerie;
    private JButton btnScanQR;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScannerQRCODE frame = new ScannerQRCODE();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ScannerQRCODE() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 831, 424);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        btnScanQR = new JButton("Scanner un QRCODE");
        btnScanQR.setBounds(10, 10, 136, 38);
        contentPane.add(btnScanQR);

        panelCamera = new JPanel();
        panelCamera.setBounds(10, 64, 300, 300);
        contentPane.add(panelCamera);
        panelCamera.setLayout(null);

        cameraPanel = new CameraPanel();
        cameraPanel.setBounds(0, 0, 300, 300);
        panelCamera.add(cameraPanel);

        JLabel lblNumSerie = new JLabel("Numéro de licencié:");
        lblNumSerie.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNumSerie.setBounds(373, 23, 149, 13);
        contentPane.add(lblNumSerie);

        textNumSerie = new JTextField();
        textNumSerie.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textNumSerie.setBounds(373, 46, 127, 29);
        contentPane.add(textNumSerie);
        textNumSerie.setColumns(10);

        JButton btnModify = new JButton("Modifier le licencié");
        btnModify.setFont(new Font("Tahoma", Font.PLAIN, 13));
        btnModify.setBounds(492, 339, 172, 21);
        contentPane.add(btnModify);

        btnScanQR.addActionListener(e -> {
            cameraPanel.startCamera();
            btnScanQR.setEnabled(false); // Désactiver le bouton de numérisation QR une fois qu'il est cliqué
        });
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

            Thread thread = new Thread(() -> {
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
                        updateTextFieldsFromJson(qrCodeText);
                        camera.release(); // Libérer la caméra
                        panelCamera.removeAll(); // Supprimer le contenu du panneau de la caméra
                        panelCamera.repaint(); // Redessiner le panneau de la caméra (vide)
                        btnScanQR.setEnabled(true); // Réactiver le bouton de numérisation QR
                        break;
                    }

                    repaint();
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

        private void updateTextFieldsFromJson(String jsonString) {
            try {
                // Parser la chaîne JSON
                JSONParser parser = new JSONParser();
                JSONObject jsonObject = (JSONObject) parser.parse(jsonString);

                // Extraire les valeurs du JSON
                String numeroLicence = (String) jsonObject.get("numero_licence");
                String nom = (String) jsonObject.get("nom");
                String prenom = (String) jsonObject.get("prenom");

                // Mettre à jour les champs de texte avec les valeurs extraites
                textNumSerie.setText(numeroLicence);
                // Mettre les valeurs de nom et prénom dans les autres champs de texte
            } catch (ParseException e) {
                e.printStackTrace();
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

