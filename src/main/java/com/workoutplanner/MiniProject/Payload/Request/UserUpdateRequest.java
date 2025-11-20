package com.workoutplanner.MiniProject.Payload.Request;

import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserUpdateRequest {
    private String fullName;
    private String email;

    @Size(min = 8, message = "PASSWORD_INVALID")
    private String passwordHash;
    private String phoneNumber;
    private String gender;
    private LocalDate birthday;

    public UserUpdateRequest(String fullName, String email, String passwordHash, String phoneNumber, String gender, LocalDate birthday) {
        this.fullName = fullName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.birthday = birthday;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
