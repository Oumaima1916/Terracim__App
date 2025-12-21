package com.myapp.model;

public class Project {
    private int id;
    private String title;
    private String client;
    private String location;
    private String description;
    private int chefId;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getClient() { return client; }
    public void setClient(String client) { this.client = client; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getChefId() { return chefId; }
    public void setChefId(int chefId) { this.chefId = chefId; }

}
