package com.workoutplanner.MiniProject.Repositories;

import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.UserInbody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserInbodyRepository extends JpaRepository<UserInbody, Integer> {
    List<UserInbody> findByUser(User user);
    Optional<UserInbody> findTopByUserOrderByMeasuredAtDesc(User user);
    Optional<UserInbody> findById(Integer id);
}