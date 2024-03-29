package M;

public class Licencie {
    private String numeroLicence;
    private String nom;
    private String prenom;
    private String sexe;

    // Constructeur, getters et setters

    public Licencie() {
    }

    public Licencie(String numeroLicence, String nom, String prenom, String sexe) {
        this.numeroLicence = numeroLicence;
        this.nom = nom;
        this.prenom = prenom;
        this.sexe = sexe;
    }

    public String getNumeroLicence() {
        return numeroLicence;
    }

    public void setNumeroLicence(String numeroLicence) {
        this.numeroLicence = numeroLicence;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

}
