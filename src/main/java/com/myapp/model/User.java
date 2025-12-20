// 4. User.java (Model)
// Path: src/main/java/com.myapp/model/User.java
// ============================================

package com.myapp.model;


public class User {

    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String hashedPassword;
    private RoleUtilisateur role;

    public User() { }

    public User(Long id, String nom, String prenom, String email,
                String hashedPassword, RoleUtilisateur role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    // =======================
    // Getters
    // =======================

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public RoleUtilisateur getRole() {
        return role;
    }

    // =======================
    // Setters
    // =======================

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setRole(RoleUtilisateur role) {
        this.role = role;
    }
}
