package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "WorkoutSchedules", schema = "workoutplanner")
public class WorkoutSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ScheduleId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "PlanId", nullable = false)
    private WorkoutPlan plan;

    @Column(name = "ScheduledDate", nullable = false)
    private LocalDate scheduledDate;

    @ColumnDefault("'Pending'")
    @Column(name = "Status", length = 20)
    private String status;

    @ColumnDefault("0")
    @Column(name = "ReminderSent")
    private Boolean reminderSent;

    @Column(name = "ScheduledTime")
    private LocalTime scheduledTime;

}