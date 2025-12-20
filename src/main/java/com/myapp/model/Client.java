package com.myapp.model;

public class Client extends User {

    private String telephone;
    private String adresse;

    public Client() {
        setRole(RoleUtilisateur.CLIENT);
    }

    public Client(Long id, String nom, String prenom,
                  String email, String motDePasse,
                  String telephone, String adresse) {
        super(id, nom, prenom, email, motDePasse, RoleUtilisateur.CLIENT);
        this.telephone = telephone;
        this.adresse = adresse;
    }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
}