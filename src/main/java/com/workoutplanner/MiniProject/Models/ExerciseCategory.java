package com.workoutplanner.MiniProject.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "ExerciseCategories", schema = "workoutplanner")
public class ExerciseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CategoryId", nullable = false)
    private Integer id;

    @Column(name = "CategoryName", nullable = false, length = 100)
    private String categoryName;

    @Column(name = "Description")
    private String description;

}