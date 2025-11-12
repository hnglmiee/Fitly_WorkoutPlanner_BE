package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "WorkoutExerciseProgress", schema = "workoutplanner")
public class WorkoutExerciseProgress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "ScheduleId", nullable = false)
    private WorkoutSchedule schedule;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "WorkoutExerciseId", nullable = false)
    private WorkoutExercise workoutExercise;

    @ColumnDefault("0")
    @Column(name = "IsCompleted")
    private Boolean isCompleted;

    @Column(name = "CompletedAt")
    private Instant completedAt;

}