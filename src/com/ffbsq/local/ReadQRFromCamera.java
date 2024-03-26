package com.ffbsq.local;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;


public class ReadQRFromCamera {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    

    public static void main(String[] args) {
        VideoCapture camera = new VideoCapture(0); // Accéder à la caméra. Le numéro 0 indique la première caméra disponible.

        if (!camera.isOpened()) {
            System.out.println("Impossible d'accéder à la caméra !");
            return;
        }

        try {
            while (true) {
                Mat frame = new Mat();
                camera.read(frame); // Capturer une image à partir de la caméra

                BufferedImage image = matToBufferedImage(frame);

                // Lire le QR code
                String qrCodeText = readQRCode(image);
                if (qrCodeText != null) {
                    System.out.println("Contenu du QR code : " + qrCodeText);
                    break; // Sortir de la boucle une fois que le QR code est trouvé
                }
            }
        } finally {
            camera.release(); // Libérer les ressources de la caméra
        }
    }

    private static BufferedImage matToBufferedImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgproc.resize(mat, mat, new Size(640, 480)); // Redimensionner la matrice pour une meilleure performance
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_BGR2RGB);
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage bufImage = null;
        try {
            bufImage = ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bufImage;
    }

    private static String readQRCode(BufferedImage image) {
        try {
            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

            Result result = new MultiFormatReader().decode(bitmap, hints);

            return result.getText();
        } catch (NotFoundException e) {
            return null; // Aucun QR code trouvé dans l'image
        }
    }
}
