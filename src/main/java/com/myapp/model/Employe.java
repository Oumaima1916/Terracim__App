package com.myapp.model;

import javafx.beans.property.*;

public class Employe {

    private final IntegerProperty id;
    private final StringProperty nom;
    private final StringProperty prenom;
    private final StringProperty email;
    private final StringProperty telephone;
    private final StringProperty poste;
    private final StringProperty chantier;
    private final StringProperty dateEmbauche;
    private final StringProperty statut;

    public Employe(int id, String nom, String prenom, String email, String telephone,
                   String poste, String chantier, String dateEmbauche, String statut) {
        this.id = new SimpleIntegerProperty(id);
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.telephone = new SimpleStringProperty(telephone);
        this.poste = new SimpleStringProperty(poste);
        this.chantier = new SimpleStringProperty(chantier);
        this.dateEmbauche = new SimpleStringProperty(dateEmbauche);
        this.statut = new SimpleStringProperty(statut);
    }

    // ===== Properties =====
    public IntegerProperty idProperty() { return id; }
    public StringProperty nomProperty() { return nom; }
    public StringProperty prenomProperty() { return prenom; }
    public StringProperty emailProperty() { return email; }
    public StringProperty telephoneProperty() { return telephone; }
    public StringProperty posteProperty() { return poste; }
    public StringProperty chantierProperty() { return chantier; }
    public StringProperty dateEmbaucheProperty() { return dateEmbauche; }
    public StringProperty statutProperty() { return statut; }

    // ===== Getters =====
    public int getId() { return id.get(); }
    public String getNom() { return nom.get(); }
    public String getPrenom() { return prenom.get(); }
    public String getEmail() { return email.get(); }
    public String getTelephone() { return telephone.get(); }
    public String getPoste() { return poste.get(); }
    public String getChantier() { return chantier.get(); }
    public String getDateEmbauche() { return dateEmbauche.get(); }
    public String getStatut() { return statut.get(); }
}
