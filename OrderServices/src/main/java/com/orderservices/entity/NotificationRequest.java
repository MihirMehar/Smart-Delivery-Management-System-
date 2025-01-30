package com.orderservices.entity;

public class NotificationRequest {
    private String to;
    private String subject;
    private String body;
    private String notificationType;

    // Getters and Setters
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

	@Override
	public String toString() {
		return "NotificationRequest [to=" + to + ", subject=" + subject + ", body=" + body + "]";
	}

	
	
	public NotificationRequest(String to, String subject, String body, String notificationType) {
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.notificationType = notificationType;
	}

	public NotificationRequest() {
		
	}

	
}