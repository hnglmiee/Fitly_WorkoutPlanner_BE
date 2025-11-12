package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "Exercises", schema = "workoutplanner")
public class Exercise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ExerciseId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 100)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "CategoryId")
    private ExerciseCategory category;

}