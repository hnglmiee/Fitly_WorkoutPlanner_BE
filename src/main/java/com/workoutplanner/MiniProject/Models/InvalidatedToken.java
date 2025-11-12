package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Date;

@Entity
public class InvalidatedToken {
    @Id
    String id;
    Date expiryTime;

    public InvalidatedToken() {
    }

    public InvalidatedToken(String id, Date expiryTime) {
        this.id = id;
        this.expiryTime = expiryTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(Date expiryTime) {
        this.expiryTime = expiryTime;
    }
}
