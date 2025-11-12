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
@Table(name = "SmsNotifications", schema = "workoutplanner")
public class SmsNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SmsId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "UserId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "ScheduleId")
    private WorkoutSchedule schedule;

    @Lob
    @Column(name = "Message", nullable = false)
    private String message;

    @Column(name = "SentAt")
    private Instant sentAt;

    @ColumnDefault("'queued'")
    @Column(name = "Status", length = 20)
    private String status;

    @Lob
    @Column(name = "ProviderResponse")
    private String providerResponse;

}