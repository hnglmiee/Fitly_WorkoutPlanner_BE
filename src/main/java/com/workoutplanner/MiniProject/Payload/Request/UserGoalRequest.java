package com.workoutplanner.MiniProject.Payload.Request;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserGoalRequest {
    private String goalName;
    private BigDecimal targetWeight;
    private BigDecimal targetBodyFatPercentage;
    private BigDecimal targetMuscleMass;
    private Integer targetWorkoutSessionsPerWeek;
    private Integer targetCaloriesPerDay;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
    private String notes;

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public BigDecimal getTargetWeight() {
        return targetWeight;
    }

    public void setTargetWeight(BigDecimal targetWeight) {
        this.targetWeight = targetWeight;
    }

    public BigDecimal getTargetBodyFatPercentage() {
        return targetBodyFatPercentage;
    }

    public void setTargetBodyFatPercentage(BigDecimal targetBodyFatPercentage) {
        this.targetBodyFatPercentage = targetBodyFatPercentage;
    }

    public BigDecimal getTargetMuscleMass() {
        return targetMuscleMass;
    }

    public void setTargetMuscleMass(BigDecimal targetMuscleMass) {
        this.targetMuscleMass = targetMuscleMass;
    }

    public Integer getTargetWorkoutSessionsPerWeek() {
        return targetWorkoutSessionsPerWeek;
    }

    public void setTargetWorkoutSessionsPerWeek(Integer targetWorkoutSessionsPerWeek) {
        this.targetWorkoutSessionsPerWeek = targetWorkoutSessionsPerWeek;
    }

    public Integer getTargetCaloriesPerDay() {
        return targetCaloriesPerDay;
    }

    public void setTargetCaloriesPerDay(Integer targetCaloriesPerDay) {
        this.targetCaloriesPerDay = targetCaloriesPerDay;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
