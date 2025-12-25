package com.workoutplanner.MiniProject.Repositories;

import com.workoutplanner.MiniProject.Models.WorkoutExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {
}