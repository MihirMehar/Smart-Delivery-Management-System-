package com.notify.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.notify.entity.NotificationRequest;
import com.notify.service.NotificationService;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @PostMapping // This maps POST requests to /notifications
    public ResponseEntity<String> sendNotification(@RequestBody NotificationRequest request) {
    	//implementing logger
    	logger.info("+ Received notification requests: {}",request);
        if (request.getTo() == null || request.getSubject() == null || request.getBody() == null) {
            return ResponseEntity.badRequest().body("Invalid notification request: all fields are required.");
        }

        String response = notificationService.sendNotification(request);
        logger.info("Notification response :{}",response);
        if (response.startsWith("Failed")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
        return ResponseEntity.ok(response);
    }
}
