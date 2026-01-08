package com.workoutplanner.MiniProject.Repositories;

import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutExercise;
import com.workoutplanner.MiniProject.Models.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutExerciseRepository extends JpaRepository<WorkoutExercise, Integer> {
    List<WorkoutExercise> findByPlanUser(User user);
    Optional<WorkoutExercise> findById(int id);
    List<WorkoutExercise> findByPlan_Id(Integer planId);
}