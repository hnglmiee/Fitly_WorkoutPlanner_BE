package com.workoutplanner.MiniProject.Payload.Request;

import java.time.LocalDate;
import java.time.LocalTime;

public class WorkoutScheduleRequest {
    private Integer planId;
    private LocalDate scheduledDate;
//    private String status;
    private LocalTime scheduledTime;
//    private Boolean reminderSent;

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public LocalTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
