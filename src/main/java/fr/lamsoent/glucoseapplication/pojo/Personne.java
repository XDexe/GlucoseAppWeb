package fr.lamsoent.glucoseapplication.pojo;

import jakarta.persistence.*;

@Entity
public class Personne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPersonne;

    private String nom;
    private String prenom;
    private String pseudo;
    private String identifiant;
    private String motDePasse;
    private String photoDeProfilUrl;

    @ManyToOne
    private Role role;

    @Transient
    private String plainTextPassword;

    public Personne() {

    }


    public int getIdPersonne() {
        return idPersonne;
    }

    public void setIdPersonne(int idPersonne) {
        this.idPersonne = idPersonne;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        System.out.println("mot de passe set " + motDePasse);
        System.out.println("mot de passe actuelle "+getMotDePasse());

        if(!motDePasse.equals(getMotDePasse()) && !motDePasse.isEmpty()) {
            System.out.println("mot de passe mit");
            this.motDePasse = Outil.hashPassWordBcrypt(motDePasse);
        }
        System.out.println("mot de passe actuelle hasher"+getMotDePasse());

    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPhotoDeProfilUrl() {
        return photoDeProfilUrl;
    }

    public void setPhotoDeProfilUrl(String photoDeProfilUrl) {
        this.photoDeProfilUrl = photoDeProfilUrl;
    }

    public String getPlainTextPassword() {
        return plainTextPassword;
    }

    public void setPlainTextPassword(String plainTextPassword) {
        this.plainTextPassword = plainTextPassword;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
