package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.WorkoutLog;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutLogResponse;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutLogRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutScheduleRepository;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutLogService implements IWorkoutLogService {
    @Autowired
    private WorkoutLogRepository workoutLogRepository;
    @Autowired
    private UserRepository userRepository;

    public WorkoutLogService(WorkoutLogRepository workoutLogRepository) {
        this.workoutLogRepository = workoutLogRepository;
    }

    @Override
    public List<WorkoutLogResponse> getAllWorkoutLog() {
        List<WorkoutLog> workoutLogs = workoutLogRepository.findAll();
        try {
            return workoutLogs.stream().map(workoutLog -> {
                WorkoutLogResponse response = new WorkoutLogResponse();
                response.setScheduleId(workoutLog.getSchedule().getId());
                response.setExerciseName(workoutLog.getExercise().getName());
                response.setActualSets(workoutLog.getActualSets());
                response.setActualReps(workoutLog.getActualReps());
                response.setActualWeight(workoutLog.getActualWeight());
                response.setNotes(workoutLog.getNotes());
                response.setLoggedAt(workoutLog.getLoggedAt());
                return response;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<WorkoutLogResponse> getMyWorkoutLog() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        List<WorkoutLog> workoutLogs = workoutLogRepository.findBySchedule_Plan_User(user);

        return workoutLogs.stream().map(workoutLog -> {
            WorkoutLogResponse response = new WorkoutLogResponse();
            response.setScheduleId(workoutLog.getSchedule().getId());
            response.setExerciseName(workoutLog.getExercise().getName());
            response.setActualSets(workoutLog.getActualSets());
            response.setActualReps(workoutLog.getActualReps());
            response.setActualWeight(workoutLog.getActualWeight());
            response.setNotes(workoutLog.getNotes());
            response.setLoggedAt(workoutLog.getLoggedAt());
            return response;
        }).collect(Collectors.toList());
    }
}
