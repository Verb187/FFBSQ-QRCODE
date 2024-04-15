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

    //getDetailsByNumeroLicence
    public Licencie getDetailsByNumeroLicence(String numeroLicence) {
        try {
            URL url = new URL("http://ffbsq.local/api/get_licencie.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    
            String postData = "numLicencie=" + numeroLicence;
    
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();
    
            System.out.println("Response Code : " + conn.getResponseCode());
            System.out.println("Response Message : " + conn.getResponseMessage());
            System.out.println("Content Type : " + conn.getContentType());
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String output;
            StringBuilder response = new StringBuilder(); // Pour stocker toute la réponse JSON
            while ((output = br.readLine()) != null) {
                response.append(output); // Concaténer chaque ligne de la réponse
            }
            System.out.println("Output from Server .... \n" + response.toString());
            
            // Créer un objet JSON à partir de la réponse complète
            JSONObject jsonObject = new JSONObject(response.toString());
            Licencie licencie = new Licencie();
            licencie.setNumeroLicence(jsonObject.getString("numlicencie"));
            licencie.setNom(jsonObject.getString("nomlicencie"));
            licencie.setPrenom(jsonObject.getString("prenomlicencie"));
            licencie.setSexe(jsonObject.getString("sexelicencie"));
            licencie.setDatedenaissance(jsonObject.getString("datedenaissance"));
            licencie.setPhotolicencie(jsonObject.getString("photolicencie"));
            licencie.setCategorielicencie(jsonObject.getString("categorielicencie"));
            licencie.setPositionlicencie(jsonObject.getString("positionlicencie"));
            licencie.setAdr_licencie(jsonObject.getString("adr_licencie"));
            licencie.setAdr_ville_licencie(jsonObject.getString("adr_ville_licencie"));
            licencie.setTel_licencie(jsonObject.getString("tel_licencie"));
            licencie.setMail_licencie(jsonObject.getString("mail_licencie"));
            licencie.setNationalite_licencie(jsonObject.getString("nationalite_licencie"));
            licencie.setClassification_licencie(jsonObject.getString("classification_licencie"));
            licencie.setValidite_CM(jsonObject.getString("validite_CM"));
            licencie.setAnnee_reprise(jsonObject.getString("annee_reprise"));
            licencie.setPremiere_licence(jsonObject.getString("premiere_licence"));
            licencie.setNumeroaffiliation(jsonObject.getString("numeroaffiliation"));
            return licencie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getDetailsByNumeroLicencieAndToken(String numeroLicence, String token) {
        JSONObject licencieDetails = new JSONObject();
        try {
            URL url = new URL("http://ffbsq.local/api/getDetailsByNumeroLicencieAndToken.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String postData = "numLicencie=" + numeroLicence + "&token=" + token;

            System.out.println("Post Data : " + postData);
            OutputStream os = conn.getOutputStream();
            os.write(postData.getBytes());
            os.flush();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String output;
                while ((output = br.readLine()) != null) {
                    response.append(output);
                }
                JSONObject jsonObject = new JSONObject(response.toString());

                System.out.println("Output from Server .... \n" + response.toString());
                
                // Mettre à jour les clés pour correspondre aux noms de colonnes dans votre base de données
                licencieDetails.put("nomlicencie", jsonObject.getString("nomlicencie"));
                licencieDetails.put("prenomlicencie", jsonObject.getString("prenomlicencie"));
                // Ajoutez d'autres détails du licencié si nécessaire
                
                return licencieDetails;
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
