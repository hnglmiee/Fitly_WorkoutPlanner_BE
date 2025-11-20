package com.workoutplanner.MiniProject.Repositories;

import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkoutScheduleRepository extends JpaRepository<WorkoutSchedule, Integer> {
    List<WorkoutSchedule> findByPlan_User(User user);
    Optional<WorkoutSchedule> findByPlan_Id(Integer planId);
    Optional<WorkoutSchedule> getWorkoutScheduleById(Integer id);

    // Đếm số buổi tập trong 7 ngày gần nhất
    // Repository
    @Query("SELECT COUNT(ws) FROM WorkoutSchedule ws WHERE ws.plan.user.id = :userId AND ws.scheduledDate >= :lastWeek")
    int countByUserIdInLastWeek(@Param("userId") Integer userId, @Param("lastWeek") LocalDate lastWeek);

}