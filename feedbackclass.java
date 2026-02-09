package com.example.signup;

public class feedbackclass {
    String feedback;
    public  feedbackclass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {

        this.feedback = feedback;
    }
    public feedbackclass(String feedback) {

        this.feedback =  feedback;
    }
}
