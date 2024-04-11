package C;

import M.Licencie;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LicencieAPIController {
    public void updateLicencie(Licencie licencie) {
        try {
            // URL de l'API
            URL url = new URL("http://ffbsq.local/api/update_licencie.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String postData = "numLicencie=" + licencie.getNumeroLicence() +
                            "&nomLicencie=" + licencie.getNom() +
                            "&prenomLicencie=" + licencie.getPrenom() +
                            "&sexeLicencie=" + licencie.getSexe() +
                            "&datedenaissance=" + licencie.getDatedenaissance() +
                            "&photoLicencie=" + licencie.getPhotolicencie() +
                            "&categorieLicencie=" + licencie.getCategorielicencie() +
                            "&positionLicencie=" + licencie.getPositionlicencie() +
                            "&adr_Licencie=" + licencie.getAdr_licencie() +
                            "&adr_ville_Licencie=" + licencie.getAdr_ville_licencie() +
                            "&tel_Licencie=" + licencie.getTel_licencie() +
                            "&mail_Licencie=" + licencie.getMail_licencie() +
                            "&nationalite_Licencie=" + licencie.getNationalite_licencie() +
                            "&classification_Licencie=" + licencie.getClassification_licencie() +
                            "&validite_CM=" + licencie.getValidite_CM() +
                            "&annee_reprise=" + licencie.getAnnee_reprise() +
                            "&premiere_licence=" + licencie.getPremiere_licence() +
                            "&numeroaffiliation=" + licencie.getNumeroaffiliation();

            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
            
            
            // Vérification de la réponse de l'API

            System.out.println("Response Code : " + conn.getResponseCode());
            System.out.println("Response Message : " + conn.getResponseMessage());
            System.out.println("Content Type : " + conn.getContentType());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

             BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
             String output;
             while ((output = br.readLine()) != null) {
                 System.out.println(output);
             }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
