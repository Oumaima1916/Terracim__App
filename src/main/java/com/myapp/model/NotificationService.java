package com.myapp.service;

import com.myapp.controller.ChefNotificationController;
import com.myapp.model.Notification;

public class NotificationService {

    private static ChefNotificationController controller;

    public static void register(ChefNotificationController c) {
        controller = c;
    }


    public static void notify(Notification n) {
        if (controller != null) {
            controller.pushNotification(n);
        }
    }
}
