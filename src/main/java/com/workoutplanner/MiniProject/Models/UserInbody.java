package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "UserInbody", schema = "workoutplanner")
public class UserInbody {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "InbodyId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "MeasuredAt")
    private Instant measuredAt;

    @Column(name = "Height", precision = 5, scale = 2)
    private BigDecimal height;

    @Column(name = "Weight", precision = 5, scale = 2)
    private BigDecimal weight;

    @Column(name = "BodyFatPercentage", precision = 5, scale = 2)
    private BigDecimal bodyFatPercentage;

    @Column(name = "MuscleMass", precision = 6, scale = 2)
    private BigDecimal muscleMass;

    @Column(name = "Notes")
    private String notes;

}