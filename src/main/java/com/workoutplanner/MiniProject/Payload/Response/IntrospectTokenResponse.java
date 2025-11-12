package com.workoutplanner.MiniProject.Payload.Response;

public class IntrospectTokenResponse {
    boolean valid;

    public IntrospectTokenResponse() {
    }

    public IntrospectTokenResponse(boolean valid) {
        this.valid = valid;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }
}
