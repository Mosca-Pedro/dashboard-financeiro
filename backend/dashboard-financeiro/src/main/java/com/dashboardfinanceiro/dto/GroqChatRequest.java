package com.dashboardfinanceiro.dto;

import java.util.List;

public class GroqChatRequest {

    private String model;
    private List<GroqMessage> messages;
    private double temperature = 0.5;

    public GroqChatRequest(String model, List<GroqMessage> messages) {
        this.model = model;
        this.messages = messages;
    }

    public String getModel() {
        return model;
    }

    public List<GroqMessage> getMessages() {
        return messages;
    }

    public double getTemperature() {
        return temperature;
    }

    public static class GroqMessage {
        private String role;
        private String content;

        public GroqMessage() {
        }

        public GroqMessage(String role, String content) {
            this.role = role;
            this.content = content;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}