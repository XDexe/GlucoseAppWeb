package fr.lamsoent.glucoseapplication.service;

import fr.lamsoent.glucoseapplication.pojo.Personne;

public class Authorisation {

    public boolean isMedecin(Personne personne) {
        return personne != null &&
                personne.getRole() != null &&
                "MEDECIN".equalsIgnoreCase(personne.getRole().getNomRole());
    }
}
