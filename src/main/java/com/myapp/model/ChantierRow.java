package com.myapp.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ChantierRow {

    private final StringProperty nom;
    private final StringProperty chef;
    private final StringProperty avancement;
    private final StringProperty budget;
    private final StringProperty statut;

    public ChantierRow(String nom, String chef,
                       String avancement, String budget,
                       String statut) {
        this.nom = new SimpleStringProperty(nom);
        this.chef = new SimpleStringProperty(chef);
        this.avancement = new SimpleStringProperty(avancement);
        this.budget = new SimpleStringProperty(budget);
        this.statut = new SimpleStringProperty(statut);
    }

    public StringProperty nomProperty() { return nom; }
    public StringProperty chefProperty() { return chef; }
    public StringProperty avancementProperty() { return avancement; }
    public StringProperty budgetProperty() { return budget; }
    public StringProperty statutProperty() { return statut; }

    public String getNom() { return nom.get(); }
}
