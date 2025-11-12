package com.workoutplanner.MiniProject.Payload.Request;

import jakarta.validation.constraints.Size;

public class UserUpdateRequest {
    private String fullName;
    private String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String passwordHash;
    private String phoneNumber;

    public UserUpdateRequest() {
    }

    public UserUpdateRequest(String fullName, String email, String passwordHash, String phoneNumber) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @Size(min = 8, message = "PASSWORD_INVALID") String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(@Size(min = 8, message = "PASSWORD_INVALID") String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
