package com.workoutplanner.MiniProject.Repositories;

import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Integer> {
    List<WorkoutLog> findBySchedule_Plan_User(User user);
}