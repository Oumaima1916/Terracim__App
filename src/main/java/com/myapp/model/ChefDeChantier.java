package com.myapp.model;

public class ChefDeChantier extends User {

    public ChefDeChantier() {
        setRole(RoleUtilisateur.CHEF_CHANTIER);
    }

    public ChefDeChantier(Long id, String nom, String prenom,
                          String email, String motDePasse) {
        super(id, nom, prenom, email, motDePasse, RoleUtilisateur.CHEF_CHANTIER);
    }
}
