package com.myapp.model;

public class Chantier {
    private int id;
    private String name;
    private String nom;
    private String client;
    private String city;
    private String ville;
    private String address;
    private String adresse;
    private String state;
    private String etat;
    private String description;
    private String desc;

    public Chantier() {}

    // id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // name / nom
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    // client
    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    // city / ville
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    // address / adresse
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }

    // state / etat
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getEtat() { return etat; }
    public void setEtat(String etat) { this.etat = etat; }

    // description / desc
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getDesc() { return desc; }
    public void setDesc(String desc) { this.desc = desc; }

    @Override
    public String toString() {
        return "Chantier{name=" + (name!=null?name:nom) + ", client=" + client + ", city=" + (city!=null?city:ville) + "}";
    }
}
