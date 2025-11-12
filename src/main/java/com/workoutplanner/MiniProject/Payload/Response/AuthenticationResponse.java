package com.workoutplanner.MiniProject.Payload.Response;

import lombok.Builder;

@Builder
public class AuthenticationResponse {
    private String token;

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //    tra ve true neu nhu login hop le, false neu ko hop le
//    private boolean authenticated;
//
//    public AuthenticationResponse() {
//    }
//
//    public AuthenticationResponse(boolean authenticated) {
//        this.authenticated = authenticated;
//    }
//
//    public boolean isAuthenticated() {
//        return authenticated;
//    }
//
//    public void setAuthenticated(boolean authenticated) {
//        this.authenticated = authenticated;
//    }
}
