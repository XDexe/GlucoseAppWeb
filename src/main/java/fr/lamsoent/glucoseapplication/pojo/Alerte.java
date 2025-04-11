package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Alerte  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAlerte;
    private Date dateAlerte;
    private boolean estAuDessusTaux;

    @OneToOne
    private Donnee donnee = new Donnee();
    @ManyToOne
    private Utilisateur utilisateur = new Utilisateur();

    public Alerte() {
    }

    public int getIdAlerte() {
        return idAlerte;
    }

    public void setIdAlerte(int idAlerte) {
        this.idAlerte = idAlerte;
    }

    public Date getDateAlerte() {
        return dateAlerte;
    }

    public void setDateAlerte(Date dateAlerte) {
        this.dateAlerte = dateAlerte;
    }

    public boolean isEstAuDessusTaux() {
        return estAuDessusTaux;
    }

    public void setEstAuDessusTaux(boolean estAuDessusTaux) {
        this.estAuDessusTaux = estAuDessusTaux;
    }

    public Donnee getDonnee() {
        return donnee;
    }

    public void setDonnee(Donnee donnee) {
        this.donnee = donnee;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
