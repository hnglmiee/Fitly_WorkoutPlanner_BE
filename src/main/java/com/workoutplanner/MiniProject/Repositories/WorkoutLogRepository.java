package com.workoutplanner.MiniProject.Repositories;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutLogRepository extends JpaRepository<WorkoutLog, Integer> {
    List<WorkoutLog> findBySchedule_Plan_User(User user);
    List<WorkoutLog> findByExercise_Id(Integer exerciseId);
    Optional<WorkoutLog> getWorkoutLogById(Integer id);
    List<WorkoutLog> findBySchedule_Id(Integer scheduleId);

    @Query("SELECT COUNT(DISTINCT wl.loggedAt) FROM WorkoutLog wl WHERE wl.schedule.plan.user = :user AND wl.loggedAt >= :start AND wl.loggedAt <= :end")
    int countWorkoutSessionInRange(@Param("user") User user, @Param("start") Instant start, @Param("end") Instant end);

    List<WorkoutLog> findByScheduleIdOrderByLoggedAtDesc(Integer scheduleId);
    @Query("SELECT wl FROM WorkoutLog wl WHERE wl.schedule.id = :scheduleId ORDER BY wl.loggedAt DESC")
    List<WorkoutLog> findLogsByScheduleId(@Param("scheduleId") Integer scheduleId);
}