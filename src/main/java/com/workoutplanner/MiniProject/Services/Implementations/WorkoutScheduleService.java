package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutPlan;
import com.workoutplanner.MiniProject.Models.WorkoutSchedule;
import com.workoutplanner.MiniProject.Payload.Request.WorkoutScheduleRequest;
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

    @Override
    public WorkoutScheduleResponseCustomer createWorkoutSchedule(WorkoutScheduleRequest request) {
        // Lay email tu token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lay thong tin cua nguoi dung co email do
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lay workout plan
        WorkoutPlan plan = workoutPlanRepository.getWorkoutPlanById(request.getPlanId()).orElseThrow(() -> new AppException(ErrorCode.WORKOUT_PLAN_NOT_EXISTED));

        if (!plan.getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.WORKOUT_PLAN_NOT_EXISTED);
        }

        // DTO request -> Entity
        WorkoutSchedule workoutSchedule = new WorkoutSchedule();
        workoutSchedule.setPlan(plan);
        workoutSchedule.setScheduledDate(request.getScheduledDate());
        workoutSchedule.setScheduledTime(request.getScheduledTime());
        workoutSchedule.setStatus("Pending");
        workoutSchedule.setReminderSent(false);

        workoutScheduleRepository.save(workoutSchedule);

        // Entity -> DTO response
        WorkoutScheduleResponseCustomer response = new WorkoutScheduleResponseCustomer();
        response.setId(workoutSchedule.getId());
        response.setPlanName(plan.getTitle());
        response.setScheduledDate(workoutSchedule.getScheduledDate());
        response.setScheduledTime(workoutSchedule.getScheduledTime());
        response.setStatus(workoutSchedule.getStatus());
        return response;
    }

    @Override
    public WorkoutScheduleResponseCustomer updateWorkoutPlan(Integer id, WorkoutScheduleRequest request) {
        // Lay email tu token
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Lay thong tin cua nguoi dung co email do
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy schedule từ DB
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.WORKOUT_SCHEDULE_NOT_EXISTED));

        // Kiểm tra quyền: schedule phải thuộc về user
        if(!workoutSchedule.getPlan().getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.WORKOUT_SCHEDULE_FORBIDDEN);
        }

        if (request.getPlanId() != null) {
            WorkoutPlan newPlan = workoutPlanRepository.getWorkoutPlanById(request.getPlanId())
                    .orElseThrow(() -> new AppException(ErrorCode.WORKOUT_PLAN_NOT_EXISTED));
            // Chỉ cho đổi sang plan của chính user
            if (!newPlan.getUser().getId().equals(user.getId())) {
                throw new AppException(ErrorCode.FORBIDDEN); // ko cho đổi sang plan của người khác
            }
            workoutSchedule.setPlan(newPlan);
        }
        if (request.getScheduledDate() != null) {
            workoutSchedule.setScheduledDate(request.getScheduledDate());
        } if(request.getScheduledTime() != null) {
            workoutSchedule.setScheduledTime(request.getScheduledTime());
        }

        workoutScheduleRepository.save(workoutSchedule);
        WorkoutScheduleResponseCustomer response = new WorkoutScheduleResponseCustomer();
        response.setId(workoutSchedule.getId());
        response.setPlanName(workoutSchedule.getPlan().getTitle());
        response.setScheduledDate(workoutSchedule.getScheduledDate());
        response.setScheduledTime(workoutSchedule.getScheduledTime());
        response.setStatus(workoutSchedule.getStatus());
        return response;
    }

    @Override
    public boolean deleteWorkoutSchedule(Integer id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        WorkoutSchedule workoutSchedule = workoutScheduleRepository.getWorkoutScheduleById(id).orElseThrow(() -> new AppException(ErrorCode.WORKOUT_SCHEDULE_NOT_EXISTED));
        if(!workoutSchedule.getPlan().getUser().getId().equals(user.getId())) {
            throw new AppException(ErrorCode.WORKOUT_SCHEDULE_FORBIDDEN);
        }

        workoutScheduleRepository.delete(workoutSchedule);
        return true;
    }

    @Override
    public boolean deleteWorkoutScheduleByAdmin(Integer id) {
        WorkoutSchedule workoutSchedule = workoutScheduleRepository.getWorkoutScheduleById(id).orElseThrow(() -> new AppException(ErrorCode.WORKOUT_SCHEDULE_NOT_EXISTED));
        workoutScheduleRepository.delete(workoutSchedule);
        return true;
    }
}
