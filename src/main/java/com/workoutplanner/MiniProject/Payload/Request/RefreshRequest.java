package com.workoutplanner.MiniProject.Payload.Request;

public class RefreshRequest {
    String token;

    public RefreshRequest() {
    }

    public RefreshRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
