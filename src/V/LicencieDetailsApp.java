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
import C.LicencieAPIController;
import M.Licencie;
import javax.swing.JButton;

public class LicencieDetailsApp extends JFrame {
    private String numeroLicence;
    private JPanel contentPane;
    private JTextField textFieldNumeroLicence;
    private JTextField textFieldNom;
    private JTextField textFieldPrenom;
    private JTextField textFieldSexe;
    private JTextField textFieldDateNaissance;
    private JTextField textFieldPhotoLicencie;
    private JTextField textFieldCategorieLicencie;
    private JTextField textFieldPositionLicencie;
    private JTextField textFieldAdrLicencie;
    private JTextField textFieldAdrVilleLicencie;
    private JTextField textFieldTelLicencie;
    private JTextField textFieldMailLicencie;
    private JTextField textFieldNationaliteLicencie;
    private JTextField textFieldClassificationLicencie;
    private JTextField textFieldValiditeCM;
    private JTextField textFieldAnneeReprise;
    private JTextField textFieldPremiereLicence;
    private JTextField textFieldNumeroAffiliation;

    private JCheckBox chckbxNewCheckBox;
    
    private LicencieController licencieController;
    private LicencieAPIController licencieAPIController;
    private JButton btnSave;

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
        setBounds(100, 100, 560, 543);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        textFieldNumeroLicence = new JTextField();
        textFieldNumeroLicence.setEditable(false);
        textFieldNumeroLicence.setBounds(145, 36, 200, 20);
        contentPane.add(textFieldNumeroLicence);
        textFieldNumeroLicence.setColumns(10);

        JLabel lblNumeroLicence = new JLabel("Numero Licence:");
        lblNumeroLicence.setBounds(10, 38, 100, 14);
        contentPane.add(lblNumeroLicence);

        JLabel lblNom = new JLabel("Nom:");
        lblNom.setBounds(10, 63, 100, 14);
        contentPane.add(lblNom);

        textFieldNom = new JTextField();
        textFieldNom.setEditable(false);
        textFieldNom.setBounds(145, 61, 200, 20);
        contentPane.add(textFieldNom);

        JLabel lblPrenom = new JLabel("Prenom:");
        lblPrenom.setBounds(10, 88, 100, 14);
        contentPane.add(lblPrenom);

        textFieldPrenom = new JTextField();
        textFieldPrenom.setEditable(false);
        textFieldPrenom.setBounds(145, 85, 200, 20);
        contentPane.add(textFieldPrenom);

        JLabel lblSexe = new JLabel("Sexe:");
        lblSexe.setBounds(10, 113, 100, 14);
        contentPane.add(lblSexe);
        
        textFieldSexe = new JTextField();
        textFieldSexe.setEditable(false);
        textFieldSexe.setBounds(145, 110, 200, 20);
        contentPane.add(textFieldSexe);

        JLabel lblDateNaissance = new JLabel("Date de naissance:");
        lblDateNaissance.setBounds(10, 138, 100, 14);
        contentPane.add(lblDateNaissance);

        textFieldDateNaissance = new JTextField();
        textFieldDateNaissance.setEditable(false);
        textFieldDateNaissance.setBounds(145, 135, 200, 20);
        contentPane.add(textFieldDateNaissance);

        JLabel lblPhotoLicencie = new JLabel("Photo du licencie:");
        lblPhotoLicencie.setBounds(10, 163, 100, 14);
        contentPane.add(lblPhotoLicencie);

        textFieldPhotoLicencie = new JTextField();
        textFieldPhotoLicencie.setEditable(false);
        textFieldPhotoLicencie.setBounds(145, 160, 200, 20);
        contentPane.add(textFieldPhotoLicencie);

        JLabel lblCategorieLicencie = new JLabel("Categorie du licencie:");
        lblCategorieLicencie.setBounds(10, 188, 100, 14);
        contentPane.add(lblCategorieLicencie);

        textFieldCategorieLicencie = new JTextField();
        textFieldCategorieLicencie.setEditable(false);
        textFieldCategorieLicencie.setBounds(145, 185, 200, 20);
        contentPane.add(textFieldCategorieLicencie);

        JLabel lblPositionLicencie = new JLabel("Position du licencie:");
        lblPositionLicencie.setBounds(10, 213, 100, 14);
        contentPane.add(lblPositionLicencie);

        textFieldPositionLicencie = new JTextField();
        textFieldPositionLicencie.setEditable(false);
        textFieldPositionLicencie.setBounds(145, 210, 200, 20);
        contentPane.add(textFieldPositionLicencie);

        JLabel lblAdrLicencie = new JLabel("Adresse du licencie:");
        lblAdrLicencie.setBounds(10, 238, 125, 14);
        contentPane.add(lblAdrLicencie);

        textFieldAdrLicencie = new JTextField();
        textFieldAdrLicencie.setEditable(false);
        textFieldAdrLicencie.setBounds(145, 235, 200, 20);
        contentPane.add(textFieldAdrLicencie);

        JLabel lblAdrVilleLicencie = new JLabel("Ville du licencie:");
        lblAdrVilleLicencie.setBounds(10, 263, 100, 14);
        contentPane.add(lblAdrVilleLicencie);

        textFieldAdrVilleLicencie = new JTextField();
        textFieldAdrVilleLicencie.setEditable(false);
        textFieldAdrVilleLicencie.setBounds(145, 260, 200, 20);
        contentPane.add(textFieldAdrVilleLicencie);

        JLabel lblTelLicencie = new JLabel("Telephone du licencie:");
        lblTelLicencie.setBounds(10, 288, 125, 14);
        contentPane.add(lblTelLicencie);

        textFieldTelLicencie = new JTextField();
        textFieldTelLicencie.setEditable(false);
        textFieldTelLicencie.setBounds(145, 285, 200, 20);
        contentPane.add(textFieldTelLicencie);

        JLabel lblMailLicencie = new JLabel("Mail du licencie:");
        lblMailLicencie.setBounds(10, 313, 100, 14);
        contentPane.add(lblMailLicencie);

        textFieldMailLicencie = new JTextField();
        textFieldMailLicencie.setEditable(false);
        textFieldMailLicencie.setBounds(145, 310, 200, 20);
        contentPane.add(textFieldMailLicencie);

        JLabel lblNationaliteLicencie = new JLabel("Nationalite du licencie:");
        lblNationaliteLicencie.setBounds(10, 338, 125, 14);
        contentPane.add(lblNationaliteLicencie);
        
        textFieldNationaliteLicencie = new JTextField();
        textFieldNationaliteLicencie.setEditable(false);
        textFieldNationaliteLicencie.setBounds(145, 335, 200, 20);
        contentPane.add(textFieldNationaliteLicencie);

        JLabel lblClassificationLicencie = new JLabel("Classification du licencie:");
        lblClassificationLicencie.setBounds(10, 363, 125, 14);
        contentPane.add(lblClassificationLicencie);

        textFieldClassificationLicencie = new JTextField();
        textFieldClassificationLicencie.setEditable(false);
        textFieldClassificationLicencie.setBounds(145, 360, 200, 20);
        contentPane.add(textFieldClassificationLicencie);

        JLabel lblValiditeCM = new JLabel("Validite CM:");
        lblValiditeCM.setBounds(10, 388, 100, 14);
        contentPane.add(lblValiditeCM);

        textFieldValiditeCM = new JTextField();
        textFieldValiditeCM.setEditable(false);
        textFieldValiditeCM.setBounds(145, 385, 200, 20);
        contentPane.add(textFieldValiditeCM);

        JLabel lblAnneeReprise = new JLabel("Annee de reprise:");
        lblAnneeReprise.setBounds(10, 413, 100, 14);
        contentPane.add(lblAnneeReprise);

        textFieldAnneeReprise = new JTextField();
        textFieldAnneeReprise.setEditable(false);
        textFieldAnneeReprise.setBounds(145, 410, 200, 20);
        contentPane.add(textFieldAnneeReprise);

        JLabel lblPremiereLicence = new JLabel("Premiere licence:");
        lblPremiereLicence.setBounds(10, 438, 100, 14);
        contentPane.add(lblPremiereLicence);

        textFieldPremiereLicence = new JTextField();
        textFieldPremiereLicence.setEditable(false);
        textFieldPremiereLicence.setBounds(145, 435, 200, 20);
        contentPane.add(textFieldPremiereLicence);

        JLabel lblNumeroAffiliation = new JLabel("Numero d'affiliation:");
        lblNumeroAffiliation.setBounds(10, 463, 100, 14);
        contentPane.add(lblNumeroAffiliation);

        textFieldNumeroAffiliation = new JTextField();
        textFieldNumeroAffiliation.setEditable(false);
        textFieldNumeroAffiliation.setBounds(145, 460, 200, 20);
        contentPane.add(textFieldNumeroAffiliation);

        



        chckbxNewCheckBox = new JCheckBox("Modifier ?");
        chckbxNewCheckBox.setBounds(368, 460, 93, 21);
        contentPane.add(chckbxNewCheckBox);
        
        JButton btnNewButton = new JButton("Retour");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                Retour();
        	}
        });
        btnNewButton.setBounds(0, 0, 85, 21);
        contentPane.add(btnNewButton);
        
        btnSave = new JButton("Sauvegarder");
        btnSave.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
                SauvegarderLicencie();
        	}
        });
        btnSave.setBounds(368, 485, 175, 21);
        contentPane.add(btnSave);
        
        chckbxNewCheckBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean editable = chckbxNewCheckBox.isSelected();
                textFieldNom.setEditable(editable);
                textFieldPrenom.setEditable(editable);
                textFieldSexe.setEditable(editable);
                textFieldDateNaissance.setEditable(editable);
                textFieldPhotoLicencie.setEditable(editable);
                textFieldCategorieLicencie.setEditable(editable);
                textFieldPositionLicencie.setEditable(editable);
                textFieldAdrLicencie.setEditable(editable);
                textFieldAdrVilleLicencie.setEditable(editable);
                textFieldTelLicencie.setEditable(editable);
                textFieldMailLicencie.setEditable(editable);
                textFieldNationaliteLicencie.setEditable(editable);
                textFieldClassificationLicencie.setEditable(editable);
                textFieldValiditeCM.setEditable(editable);
                textFieldAnneeReprise.setEditable(editable);
                textFieldPremiereLicence.setEditable(editable);
                textFieldNumeroAffiliation.setEditable(editable);
            }
        });
    }

    
    public LicencieDetailsApp(String numeroLicence) {
        this();
        this.numeroLicence = numeroLicence;
        this.licencieController = new LicencieController();
        this.licencieAPIController = new LicencieAPIController();
        afficherDetailsLicencie();
    }

    public String getNumeroLicenceFromTextField() {
        return textFieldNumeroLicence.getText();
    }


    private void afficherDetailsLicencie() {
        Licencie licencie = licencieAPIController.getDetailsByNumeroLicence(numeroLicence);
        textFieldNumeroLicence.setText(licencie.getNumeroLicence());
        textFieldNom.setText(licencie.getNom());
        textFieldPrenom.setText(licencie.getPrenom());
        textFieldSexe.setText(licencie.getSexe());
        textFieldDateNaissance.setText(licencie.getDatedenaissance());
        textFieldPhotoLicencie.setText(licencie.getPhotolicencie());
        textFieldCategorieLicencie.setText(licencie.getCategorielicencie());
        textFieldPositionLicencie.setText(licencie.getPositionlicencie());
        textFieldAdrLicencie.setText(licencie.getAdr_licencie());
        textFieldAdrVilleLicencie.setText(licencie.getAdr_ville_licencie());
        textFieldTelLicencie.setText(licencie.getTel_licencie());
        textFieldMailLicencie.setText(licencie.getMail_licencie());
        textFieldNationaliteLicencie.setText(licencie.getNationalite_licencie());
        textFieldClassificationLicencie.setText(licencie.getClassification_licencie());
        textFieldValiditeCM.setText(licencie.getValidite_CM());
        textFieldAnneeReprise.setText(licencie.getAnnee_reprise());
        textFieldPremiereLicence.setText(licencie.getPremiere_licence());
        textFieldNumeroAffiliation.setText(licencie.getNumeroaffiliation());
    }

   private void SauvegarderLicencie() {
    if (textFieldNumeroLicence.getText().isEmpty() || textFieldNom.getText().isEmpty()) {
        showErrorMessage("Les champs obligatoires doivent être remplis.");
        return;
    }

    Licencie licencie = new Licencie();
    licencie.setNumeroLicence(textFieldNumeroLicence.getText());
    licencie.setNom(textFieldNom.getText());
    licencie.setPrenom(textFieldPrenom.getText());
    licencie.setSexe(textFieldSexe.getText());
    licencie.setDatedenaissance(textFieldDateNaissance.getText());
    licencie.setPhotolicencie(textFieldPhotoLicencie.getText());
    licencie.setCategorielicencie(textFieldCategorieLicencie.getText());
    licencie.setPositionlicencie(textFieldPositionLicencie.getText());
    licencie.setAdr_licencie(textFieldAdrLicencie.getText());
    licencie.setAdr_ville_licencie(textFieldAdrVilleLicencie.getText());
    licencie.setTel_licencie(textFieldTelLicencie.getText());
    licencie.setMail_licencie(textFieldMailLicencie.getText());
    licencie.setNationalite_licencie(textFieldNationaliteLicencie.getText());
    licencie.setClassification_licencie(textFieldClassificationLicencie.getText());
    licencie.setValidite_CM(textFieldValiditeCM.getText());
    licencie.setAnnee_reprise(textFieldAnneeReprise.getText());
    licencie.setPremiere_licence(textFieldPremiereLicence.getText());
    licencie.setNumeroaffiliation(textFieldNumeroAffiliation.getText());

    // Appel de la méthode pour mettre à jour le licencie via l'API
    licencieAPIController.updateLicencie(licencie);
}

    public void Retour() {
        this.dispose();
        refreshScanQRCODEApp();
    }

    public void showErrorMessage(String message) {
    }

    public void refreshScanQRCODEApp() {
        ScanQRCODEApp scanQRCODEApp = new ScanQRCODEApp();
    scanQRCODEApp.setVisible(true);
    }
}
