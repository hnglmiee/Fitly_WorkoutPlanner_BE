package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Models.WorkoutPlan;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutPlanResponse;
import com.workoutplanner.MiniProject.Repositories.WorkoutPlanRepository;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkoutPlanService implements IWorkoutPlanService {
    @Autowired
    private WorkoutPlanRepository workoutPlanRepository;

    public WorkoutPlanService(WorkoutPlanRepository workoutPlanRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public List<WorkoutPlanResponse> getAllWorkoutPlan() {
        List<WorkoutPlan> workoutPlans = workoutPlanRepository.findAll();
        try {
            return workoutPlans.stream().map(workoutPlan -> {
                WorkoutPlanResponse workoutPlanResponse = new WorkoutPlanResponse();
                workoutPlanResponse.setId(workoutPlan.getId());
                workoutPlanResponse.setFullName(workoutPlan.getUser().getFullName());
                workoutPlanResponse.setTitle(workoutPlan.getTitle());
                workoutPlanResponse.setNotes(workoutPlan.getNotes());
                workoutPlanResponse.setCreatedAt(workoutPlan.getCreatedAt());
                workoutPlanResponse.setUpdatedAt(workoutPlan.getUpdatedAt());
                return workoutPlanResponse;
            }).collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
