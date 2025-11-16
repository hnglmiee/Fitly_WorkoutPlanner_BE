package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "WorkoutLogs", schema = "workoutplanner")
public class WorkoutLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LogId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ScheduleId", nullable = false)
    private WorkoutSchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ExerciseId", nullable = false)
    private Exercise exercise;

    @Column(name = "ActualSets")
    private Integer actualSets;

    @Column(name = "ActualReps")
    private Integer actualReps;

    @Column(name = "ActualWeight", precision = 6, scale = 2)
    private BigDecimal actualWeight;

    @Column(name = "Notes")
    private String notes;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "LoggedAt")
    private Instant loggedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public WorkoutSchedule getSchedule() {
        return schedule;
    }

    public void setSchedule(WorkoutSchedule schedule) {
        this.schedule = schedule;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Integer getActualSets() {
        return actualSets;
    }

    public void setActualSets(Integer actualSets) {
        this.actualSets = actualSets;
    }

    public Integer getActualReps() {
        return actualReps;
    }

    public void setActualReps(Integer actualReps) {
        this.actualReps = actualReps;
    }

    public BigDecimal getActualWeight() {
        return actualWeight;
    }

    public void setActualWeight(BigDecimal actualWeight) {
        this.actualWeight = actualWeight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getLoggedAt() {
        return loggedAt;
    }

    public void setLoggedAt(Instant loggedAt) {
        this.loggedAt = loggedAt;
    }
}