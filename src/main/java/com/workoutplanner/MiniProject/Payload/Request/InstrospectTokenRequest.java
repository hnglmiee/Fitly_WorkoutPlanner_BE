package com.workoutplanner.MiniProject.Payload.Request;

// Kiem tra xem token co hop le ko, co phai la do he thong tao ra hay ko
public class InstrospectTokenRequest {
    public String token;

    public InstrospectTokenRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
