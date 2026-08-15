package com.dashboardfinanceiro.dto;

import java.util.List;

public class GroqChatResponse {

    private List<Choice> choices;

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    public static class Choice {
        private GroqChatRequest.GroqMessage message;

        public GroqChatRequest.GroqMessage getMessage() {
            return message;
        }

        public void setMessage(GroqChatRequest.GroqMessage message) {
            this.message = message;
        }
    }
}