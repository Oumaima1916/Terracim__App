package com.myapp.session;

public class UserSession {

    private static int userId;
    private static String role;
    private static String email;

    public static void start(int id, String userRole, String userEmail) {
        userId = id;
        role = userRole;
        email = userEmail;
    }

    public static int getUserId() {
        return userId;
    }

    public static String getRole() {
        return role;
    }

    public static String getEmail() {
        return email;
    }

    public static void clear() {
        userId = 0;
        role = null;
        email = null;
    }
}
