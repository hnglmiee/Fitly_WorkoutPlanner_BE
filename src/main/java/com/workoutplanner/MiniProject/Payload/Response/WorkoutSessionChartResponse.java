package com.workoutplanner.MiniProject.Payload.Response;

public class WorkoutSessionChartResponse {
    private String lable;
    private Integer sessions;

    public WorkoutSessionChartResponse(String lable, Integer sessions) {
        this.lable = lable;
        this.sessions = sessions;
    }

    public String getLable() {
        return lable;
    }

    public void setLable(String lable) {
        this.lable = lable;
    }

    public Integer getSessions() {
        return sessions;
    }

    public void setSessions(Integer sessions) {
        this.sessions = sessions;
    }
}
