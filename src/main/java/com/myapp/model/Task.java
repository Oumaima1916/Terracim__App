package com.myapp.model;

public class Task {

    private int id;
    private int projectId;
    private String name;
    private double budgetPrevu;
    private double budgetUtilise;
    private String resume;

    // getters & setters

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getProjectId() { return projectId; }
    public void setProjectId(int projectId) { this.projectId = projectId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getBudgetPrevu() { return budgetPrevu; }
    public void setBudgetPrevu(double budgetPrevu) { this.budgetPrevu = budgetPrevu; }

    public double getBudgetUtilise() { return budgetUtilise; }
    public void setBudgetUtilise(double budgetUtilise) { this.budgetUtilise = budgetUtilise; }

    public String getResume() { return resume; }
    public void setResume(String resume) { this.resume = resume; }
}
