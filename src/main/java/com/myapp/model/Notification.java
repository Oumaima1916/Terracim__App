package com.myapp.model;

import java.time.LocalDateTime;

public class Notification {

    private int id;
    private int userId;
    private String title;
    private String message;
    private String type;
    private int relatedId;
    private boolean isRead;
    private LocalDateTime createdAt;

    public Notification() {}


    public int getId() { return id; }
    public int getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getType() { return type; }
    public int getRelatedId() { return relatedId; }
    public boolean isRead() { return isRead; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    // ===== SETTERS =====
    public void setId(int id) { this.id = id; }
    public void setUserId(int userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setMessage(String message) { this.message = message; }
    public void setType(String type) { this.type = type; }
    public void setRelatedId(int relatedId) { this.relatedId = relatedId; }
    public void setRead(boolean read) { isRead = read; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
