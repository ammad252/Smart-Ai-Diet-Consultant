package com.example.signup;

public class postclass {
    String post;
    public  postclass() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public String getpost() {
        return post;
    }

    public void setPost(String post) {
        this.post=  post;
    }

    public postclass(String post) {
        this.post =  post;
    }
}
