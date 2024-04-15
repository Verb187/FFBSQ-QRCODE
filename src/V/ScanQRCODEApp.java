package V;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import C.LicencieAPIController;
import C.LicencieController;

public class ScanQRCODEApp extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JPanel contentPane;
    private JPanel panelCamera;
    private CameraPanel cameraPanel;
    private JTextField textNumSerie;
    private JButton btnScanQR;
    private JTextField textPrenom;
    private JTextField textNom;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                ScanQRCODEApp frame = new ScanQRCODEApp();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public ScanQRCODEApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 872, 432);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        btnScanQR = new JButton("Scanner un QRCODE");
        btnScanQR.setFont(new Font("Sitka Small", Font.BOLD, 15));
        btnScanQR.setBounds(10, 10, 300, 38);
        contentPane.add(btnScanQR);

        panelCamera = new JPanel();
        panelCamera.setBounds(10, 64, 300, 300);
        contentPane.add(panelCamera);
        panelCamera.setLayout(null);

        cameraPanel = new CameraPanel();
        cameraPanel.setBounds(0, 0, 300, 300);
        panelCamera.add(cameraPanel);

        JLabel lblNumSerie = new JLabel("Numéro de licencié");
        lblNumSerie.setHorizontalAlignment(SwingConstants.CENTER);
        lblNumSerie.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblNumSerie.setBounds(373, 10, 462, 26);
        contentPane.add(lblNumSerie);

        textNumSerie = new JTextField();
        textNumSerie.setHorizontalAlignment(SwingConstants.CENTER);
        textNumSerie.setEditable(false);
        textNumSerie.setFont(new Font("Tahoma", Font.PLAIN, 20));
        textNumSerie.setBounds(373, 46, 462, 29);
        contentPane.add(textNumSerie);
        textNumSerie.setColumns(10);


        JButton btnModify = new JButton("VOIR LE LICENCIE");
        btnModify.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String numeroLicence = textNumSerie.getText();
                ouvrirLicencie(numeroLicence); // 
            }
        });
        btnModify.setFont(new Font("MS UI Gothic", Font.BOLD, 18));
        btnModify.setBounds(373, 165, 462, 45);
        contentPane.add(btnModify);

        JLabel lblPrnom = new JLabel("Prénom");
        lblPrnom.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblPrnom.setBounds(373, 83, 149, 13);
        contentPane.add(lblPrnom);

        textPrenom = new JTextField();
        textPrenom.setEditable(false);
        textPrenom.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textPrenom.setColumns(10);
        textPrenom.setBounds(373, 106, 185, 29);
        contentPane.add(textPrenom);

        textNom = new JTextField();
        textNom.setEditable(false);
        textNom.setFont(new Font("Tahoma", Font.PLAIN, 13));
        textNom.setColumns(10);
        textNom.setBounds(650, 106, 185, 29);
        contentPane.add(textNom);

        JLabel lblNom = new JLabel("Nom");
        lblNom.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNom.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNom.setBounds(708, 83, 127, 13);
        contentPane.add(lblNom);

        btnScanQR.addActionListener(e -> {
            btnScanQR.setEnabled(false);
            cameraPanel.startCamera();
        });
    }

    class CameraPanel extends JPanel {
        private VideoCapture camera;
        private BufferedImage image;

        public CameraPanel() {
            setPreferredSize(new Dimension(300, 300));
        }

        public void stopCamera() {
            camera.release();
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
                        btnScanQR.setEnabled(true);
                        camera.release();
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
                JSONObject jsonObject = new JSONObject(jsonString);
        
                String numeroLicence = jsonObject.getString("numero_licence");
                String token = jsonObject.getString("token");
                textNumSerie.setText(numeroLicence);
        
                getDetailsByNumeroLicencieAndToken(numeroLicence, token);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        

       
        private void getDetailsByNumeroLicencieAndToken(String numeroLicence, String token) {
            LicencieAPIController licencieAPIController = new LicencieAPIController();
            JSONObject licencieDetails = null;
            try {
                licencieDetails = licencieAPIController.getDetailsByNumeroLicencieAndToken(numeroLicence, token);
                if (licencieDetails != null) {
                    textNom.setText(licencieDetails.getString("nomlicencie"));
                    textPrenom.setText(licencieDetails.getString("prenomlicencie"));
                } else {
                    JOptionPane.showMessageDialog(null, "Licencié introuvable!");
                }
            } catch (JSONException e) {
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

    public void ouvrirLicencie(String numeroLicence) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LicencieDetailsApp frame = new LicencieDetailsApp(numeroLicence); // Passer le numéro de licence
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
