package com.workoutplanner.MiniProject.Payload.Response;

import java.math.BigDecimal;
import java.time.Instant;

public class UserInbodyResponse {
    private Integer id;
    private String fullName;
    private Instant measuredAt;
    private BigDecimal height;
    private BigDecimal weight;
    private BigDecimal bodyFatPercentage;
    private BigDecimal muscleMass;
    private BigDecimal bmi;
    private BigDecimal leanBodyMass;
    private String bodyFatTrend; // "up", "down", "stable"
    private String muscleMassTrend; // "up", "down", "stable"
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Instant getMeasuredAt() {
        return measuredAt;
    }

    public void setMeasuredAt(Instant measuredAt) {
        this.measuredAt = measuredAt;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public BigDecimal getLeanBodyMass() {
        return leanBodyMass;
    }

    public void setLeanBodyMass(BigDecimal leanBodyMass) {
        this.leanBodyMass = leanBodyMass;
    }

    public String getBodyFatTrend() {
        return bodyFatTrend;
    }

    public void setBodyFatTrend(String bodyFatTrend) {
        this.bodyFatTrend = bodyFatTrend;
    }

    public String getMuscleMassTrend() {
        return muscleMassTrend;
    }

    public void setMuscleMassTrend(String muscleMassTrend) {
        this.muscleMassTrend = muscleMassTrend;
    }
}
