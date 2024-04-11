package M;

import DAO.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LicencieModele {
    public LicencieModele() {
        new DBConnection();
    }

    public Licencie getLicencieDetails(String numeroLicence) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        Licencie licencie = null;

        try {
            connection = DBConnection.getConnection();
            String query = "SELECT * FROM licencie WHERE numlicencie = ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, numeroLicence);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Récupérer les données du résultat de la requête
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return licencie;
    }
}
