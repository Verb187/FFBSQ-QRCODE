package C;

import DAO.DBConnection;
import M.Licencie;

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
                    // Ajoutez les autres attributs de Licencie selon votre schéma de base de données
                    return licencie;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Gérez l'erreur de manière appropriée
        }
        return null;
    }
}
