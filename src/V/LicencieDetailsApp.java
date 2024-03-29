package V;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import C.LicencieController;
import M.Licencie;

public class LicencieDetailsApp extends JFrame {
    private String numeroLicence;
    private JPanel contentPane;
    private JTextField textFieldNumeroLicence;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JCheckBox chckbxNewCheckBox;
    
    private LicencieController licencieController;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LicencieDetailsApp frame = new LicencieDetailsApp();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
    public LicencieDetailsApp() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textFieldNumeroLicence = new JTextField();
        textFieldNumeroLicence.setEditable(false);
        textFieldNumeroLicence.setBounds(120, 8, 200, 20);
        contentPane.add(textFieldNumeroLicence);
        textFieldNumeroLicence.setColumns(10);

        JLabel lblNumeroLicence = new JLabel("Numero Licence:");
        lblNumeroLicence.setBounds(10, 11, 100, 14);
        contentPane.add(lblNumeroLicence);

        JLabel lblNom = new JLabel("Nom:");
        lblNom.setBounds(10, 36, 100, 14);
        contentPane.add(lblNom);

        textFieldNom = new JTextField();
        textFieldNom.setEditable(false);
        textFieldNom.setBounds(120, 33, 200, 20);
        contentPane.add(textFieldNom);

        JLabel lblPrenom = new JLabel("Prenom:");
        lblPrenom.setBounds(10, 61, 100, 14);
        contentPane.add(lblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setEditable(false);
        textFieldPrenom.setBounds(120, 58, 200, 20);
        contentPane.add(textFieldPrenom);
        
        chckbxNewCheckBox = new JCheckBox("Modifier ?");
        chckbxNewCheckBox.setBounds(337, 7, 93, 21);
        contentPane.add(chckbxNewCheckBox);
        
        chckbxNewCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean editable = chckbxNewCheckBox.isSelected();
                textFieldNumeroLicence.setEditable(editable);
                textFieldNom.setEditable(editable);
                textFieldPrenom.setEditable(editable);
            }
        });
    }

    
    public LicencieDetailsApp(String numeroLicence) {
        this(); // Appel au constructeur par défaut pour initialiser les composants de l'interface
        this.numeroLicence = numeroLicence;
        this.licencieController = new LicencieController(); // Instancier le contrôleur
        afficherDetailsLicencie(); // Afficher les détails du licencié
    }

    public String getNumeroLicenceFromTextField() {
        return textFieldNumeroLicence.getText();
    }

    private void afficherDetailsLicencie() {
        if (numeroLicence != null) {
            Licencie licencie = licencieController.getDetailsByNumeroLicence(numeroLicence);
            if (licencie != null) {
                System.out.println("Licencie trouvé: " + licencie.getNom() + " " + licencie.getPrenom() + " " + licencie.getSexe());
                textFieldNumeroLicence.setText(licencie.getNumeroLicence());
                textFieldNom.setText(licencie.getNom());
                textFieldPrenom.setText(licencie.getPrenom());
            } else {
                showErrorMessage("Aucun licencié trouvé pour ce numéro de licence.");
            }
        } else {
            showErrorMessage("Numéro de licence non spécifié.");
        }
    }

    public void showErrorMessage(String message) {
        // Implémentez l'affichage du message d'erreur selon vos besoins
    }
}
