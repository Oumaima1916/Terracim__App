package com.myapp.model;

public enum RoleUtilisateur {
    ADMIN(1),
    CLIENT(2),
    CHEF_CHANTIER(3),
    SECRETAIRE(4),
    DIRECTEUR(5);

    private final int id;

    RoleUtilisateur(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Retourne le RoleUtilisateur correspondant à l'id (ou null si introuvable).
     */
    public static RoleUtilisateur fromId(int id) {
        for (RoleUtilisateur r : values()) {
            if (r.id == id) return r;
        }
        return null; // ou lancer une exception si tu préfères
    }
}
