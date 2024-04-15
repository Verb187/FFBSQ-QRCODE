package C;

import DAO.DBConnection;
import M.Licencie;

import org.json.simple.JSONObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LicencieController {
    public Licencie getDetailsByNumeroLicence(String numeroLicence) {
        String query = "SELECT * FROM licencie WHERE numlicencie = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroLicence);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Licencie licencie = new Licencie();
                    licencie.setNumeroLicence(rs.getString("numlicencie"));
                    licencie.setNom(rs.getString("nomlicencie"));
                    licencie.setPrenom(rs.getString("prenomlicencie"));
                    licencie.setSexe(rs.getString("sexelicencie"));
                    licencie.setDatedenaissance(rs.getString("datedenaissance"));
                    licencie.setPhotolicencie(rs.getString("photolicencie"));
                    licencie.setCategorielicencie(rs.getString("categorielicencie"));
                    licencie.setPositionlicencie(rs.getString("positionlicencie"));
                    licencie.setAdr_licencie(rs.getString("adr_licencie"));
                    licencie.setAdr_ville_licencie(rs.getString("adr_ville_licencie"));
                    licencie.setTel_licencie(rs.getString("tel_licencie"));
                    licencie.setMail_licencie(rs.getString("mail_licencie"));
                    licencie.setNationalite_licencie(rs.getString("nationalite_licencie"));
                    licencie.setClassification_licencie(rs.getString("classification_licencie"));
                    licencie.setValidite_CM(rs.getString("validite_CM"));
                    licencie.setAnnee_reprise(rs.getString("annee_reprise"));
                    licencie.setPremiere_licence(rs.getString("premiere_licence"));
                    licencie.setNumeroaffiliation(rs.getString("numeroaffiliation"));

                    return licencie;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'erreur de manière appropriée
        }
        return null;
    }

    public void updateLicencie(Licencie licencie) {
        String query = "UPDATE licencie SET nomlicencie = ?, prenomlicencie = ?, sexelicencie = ?, datedenaissance = ?, photolicencie = ?, categorielicencie = ?, positionlicencie = ?, adr_licencie = ?, adr_ville_licencie = ?, tel_licencie = ?, mail_licencie = ?, nationalite_licencie = ?, classification_licencie = ?, validite_CM = ?, annee_reprise = ?, premiere_licence = ?, numeroaffiliation = ? WHERE numlicencie = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, licencie.getNom());
            stmt.setString(2, licencie.getPrenom());
            stmt.setString(3, licencie.getSexe());
            stmt.setString(4, licencie.getDatedenaissance());
            stmt.setString(5, licencie.getPhotolicencie());
            stmt.setString(6, licencie.getCategorielicencie());
            stmt.setString(7, licencie.getPositionlicencie());
            stmt.setString(8, licencie.getAdr_licencie());
            stmt.setString(9, licencie.getAdr_ville_licencie());
            stmt.setString(10, licencie.getTel_licencie());
            stmt.setString(11, licencie.getMail_licencie());
            stmt.setString(12, licencie.getNationalite_licencie());
            stmt.setString(13, licencie.getClassification_licencie());
            stmt.setString(14, licencie.getValidite_CM());
            stmt.setString(15, licencie.getAnnee_reprise());
            stmt.setString(16, licencie.getPremiere_licence());
            stmt.setString(17, licencie.getNumeroaffiliation());
            stmt.setString(18, licencie.getNumeroLicence());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'erreur de manière appropriée
        }
    }

    public static JSONObject getDetailsByNumeroLicencieAndToken(String numeroLicence, String token) {
        JSONObject licencieDetails = new JSONObject();
        String query = "SELECT * FROM licencie WHERE numlicencie = ? AND token = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, numeroLicence);
            stmt.setString(2, token);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Licencie licencie = new Licencie();
                    licencie.setNumeroLicence(rs.getString("numlicencie"));
                    licencie.setNom(rs.getString("nomlicencie"));
                    licencie.setPrenom(rs.getString("prenomlicencie"));
                    licencie.setSexe(rs.getString("sexelicencie"));
                    licencie.setDatedenaissance(rs.getString("datedenaissance"));
                    licencie.setPhotolicencie(rs.getString("photolicencie"));
                    licencie.setCategorielicencie(rs.getString("categorielicencie"));
                    licencie.setPositionlicencie(rs.getString("positionlicencie"));
                    licencie.setAdr_licencie(rs.getString("adr_licencie"));
                    licencie.setAdr_ville_licencie(rs.getString("adr_ville_licencie"));
                    licencie.setTel_licencie(rs.getString("tel_licencie"));
                    licencie.setMail_licencie(rs.getString("mail_licencie"));
                    licencie.setNationalite_licencie(rs.getString("nationalite_licencie"));
                    licencie.setClassification_licencie(rs.getString("classification_licencie"));
                    licencie.setValidite_CM(rs.getString("validite_CM"));
                    licencie.setAnnee_reprise(rs.getString("annee_reprise"));
                    licencie.setPremiere_licence(rs.getString("premiere_licence"));
                    licencie.setNumeroaffiliation(rs.getString("numeroaffiliation"));
                    
                    // Ajouter les détails du licencié à l'objet JSONObject
                    licencieDetails.put("numero_licence", licencie.getNumeroLicence());
                    licencieDetails.put("nom", licencie.getNom());
                    licencieDetails.put("prenom", licencie.getPrenom());
                    // Ajoutez d'autres détails si nécessaire
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'erreur de manière appropriée
        }
        return licencieDetails;
    }
    
}


