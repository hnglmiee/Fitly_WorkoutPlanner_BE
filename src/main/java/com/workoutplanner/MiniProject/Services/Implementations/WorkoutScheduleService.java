package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutSchedule;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutScheduleResponseAdmin;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutScheduleResponseCustomer;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutPlanRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutScheduleRepository;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutScheduleService implements IWorkoutScheduleService {
    @Autowired
    private WorkoutScheduleRepository workoutScheduleRepository;
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;
    @Autowired
    private UserRepository userRepository;

    public WorkoutScheduleService(WorkoutScheduleRepository workoutScheduleRepository, WorkoutPlanRepository workoutPlanRepository) {
        this.workoutScheduleRepository = workoutScheduleRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public List<WorkoutScheduleResponseAdmin> getAllWorkoutSchedule() {
        List<WorkoutSchedule> workoutSchedules = workoutScheduleRepository.findAll();
        try {
            return workoutSchedules.stream().map(workoutSchedule -> {
                WorkoutScheduleResponseAdmin response = new WorkoutScheduleResponseAdmin();
                response.setId(workoutSchedule.getId());
                response.setPlanName(workoutSchedule.getPlan().getTitle());
                response.setScheduledDate(workoutSchedule.getScheduledDate());
                response.setScheduledTime(workoutSchedule.getScheduledTime());
                response.setStatus(workoutSchedule.getStatus());
                response.setReminderSent(workoutSchedule.getReminderSent());
                response.setFullName(workoutSchedule.getPlan().getUser().getFullName());
                return response;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkoutScheduleResponseCustomer> getMySchedule() {
        // Lay thong tin user = email
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lay lich theo user
        List<WorkoutSchedule> schedules = workoutScheduleRepository.findByPlan_User(user);

        // Entity -> DTO
        return schedules.stream().map(schedule -> {
            WorkoutScheduleResponseCustomer response = new WorkoutScheduleResponseCustomer();
            response.setId(schedule.getId());
            response.setPlanName(schedule.getPlan().getTitle());
            response.setScheduledDate(schedule.getScheduledDate());
            response.setScheduledTime(schedule.getScheduledTime());
            response.setStatus(schedule.getStatus());
            return response;
        }).toList();
    }

    @Override
    public WorkoutScheduleResponseAdmin getWorkoutScheduleById(Integer id) {
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(id).orElse(null);
        WorkoutScheduleResponseAdmin response = new WorkoutScheduleResponseAdmin();
        response.setId(workoutSchedule.getId());
        response.setPlanName(workoutSchedule.getPlan().getTitle());
        response.setScheduledDate(workoutSchedule.getScheduledDate());
        response.setScheduledTime(workoutSchedule.getScheduledTime());
        response.setStatus(workoutSchedule.getStatus());
        response.setReminderSent(workoutSchedule.getReminderSent());
        response.setFullName(workoutSchedule.getPlan().getUser().getFullName());
        return response;
    }
}
