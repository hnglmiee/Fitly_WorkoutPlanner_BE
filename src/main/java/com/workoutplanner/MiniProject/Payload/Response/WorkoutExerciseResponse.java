package com.workoutplanner.MiniProject.Payload.Response;

import com.workoutplanner.MiniProject.Models.Exercise;
import com.workoutplanner.MiniProject.Models.WorkoutPlan;

import java.math.BigDecimal;

public class WorkoutExerciseResponse {
    private Integer workoutExerciseId;

    private Integer planId;
    private String planTitle;

    private Integer exerciseId;
    private String exerciseName;

    private Integer sets;
    private Integer reps;
    private BigDecimal weight;
    private String comments;

    public Integer getWorkoutExerciseId() {
        return workoutExerciseId;
    }

    public void setWorkoutExerciseId(Integer workoutExerciseId) {
        this.workoutExerciseId = workoutExerciseId;
    }

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public Integer getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Integer exerciseId) {
        this.exerciseId = exerciseId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
