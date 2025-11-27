package com.workoutplanner.MiniProject.Payload.Request;

import java.math.BigDecimal;
import java.time.Instant;

public class UserInBodyUpdateRequest {
    private Integer age;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bodyFatPercentage;
    private BigDecimal muscleMass;
    private Instant measuredAt;
    private String notes;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getBodyFatPercentage() {
        return bodyFatPercentage;
    }

    public void setBodyFatPercentage(BigDecimal bodyFatPercentage) {
        this.bodyFatPercentage = bodyFatPercentage;
    }

    public BigDecimal getMuscleMass() {
        return muscleMass;
    }

    public void setMuscleMass(BigDecimal muscleMass) {
        this.muscleMass = muscleMass;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(Instant measuredAt) {
        this.measuredAt = measuredAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
