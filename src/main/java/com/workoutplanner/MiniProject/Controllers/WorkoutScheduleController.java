package com.workoutplanner.MiniProject.Controllers;

import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Payload.Request.WorkoutPlanRequest;
import com.workoutplanner.MiniProject.Payload.Request.WorkoutScheduleRequest;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutPlanResponse;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutScheduleResponseAdmin;
import com.workoutplanner.MiniProject.Payload.Response.WorkoutScheduleResponseCustomer;
import com.workoutplanner.MiniProject.Services.Interfaces.IWorkoutScheduleService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.WorkoutSchedule.ROOT)
public class WorkoutScheduleController {
    private final IWorkoutScheduleService workoutScheduleService;

    public WorkoutScheduleController(IWorkoutScheduleService workoutScheduleService) {
        this.workoutScheduleService = workoutScheduleService;
    }

    @GetMapping("/admin")
    public ApiResponse<List<WorkoutScheduleResponseAdmin>> getAllWorkoutSchedules() {
        ApiResponse<List<WorkoutScheduleResponseAdmin>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutScheduleService.getAllWorkoutSchedule());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @GetMapping("/my-schedules")
    public ApiResponse<List<WorkoutScheduleResponseCustomer>> getMySchedules() {
        ApiResponse<List<WorkoutScheduleResponseCustomer>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutScheduleService.getMySchedule());
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @GetMapping("/admin/{id}")
    public ApiResponse<WorkoutScheduleResponseAdmin> getScheduleById(@PathVariable int id) {
        ApiResponse<WorkoutScheduleResponseAdmin> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutScheduleService.getWorkoutScheduleById(id));
        apiResponse.setMessage("Get data successfully!");
        return apiResponse;
    }

    @PostMapping()
    public ApiResponse<WorkoutScheduleResponseCustomer> createWorkoutSchedule(@RequestBody @Valid WorkoutScheduleRequest request) {
        ApiResponse<WorkoutScheduleResponseCustomer> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutScheduleService.createWorkoutSchedule(request));
        apiResponse.setMessage("Create data successfully!");
        return apiResponse;
    }

    @PutMapping("/{id}")
    public ApiResponse<WorkoutScheduleResponseCustomer> updateWorkoutSchedule(@PathVariable Integer id, @RequestBody WorkoutScheduleRequest request) {
        ApiResponse<WorkoutScheduleResponseCustomer> apiResponse = new ApiResponse<>();
        apiResponse.setResult(workoutScheduleService.updateWorkoutPlan(id, request));
        apiResponse.setMessage("Update data successfully!");
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Boolean> deleteWorkoutSchedule(@PathVariable Integer id) {
        workoutScheduleService.deleteWorkoutSchedule(id);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(Boolean.TRUE);
        apiResponse.setMessage("Delete data successfully!");
        return apiResponse;
    }

    @DeleteMapping("/admin/{id}")
    public ApiResponse<Boolean> deleteWorkoutScheduleByAdmin(@PathVariable Integer id) {
        workoutScheduleService.deleteWorkoutScheduleByAdmin(id);
        ApiResponse<Boolean> apiResponse = new ApiResponse<>();
        apiResponse.setResult(Boolean.TRUE);
        apiResponse.setMessage("Delete data successfully!");
        return apiResponse;
    }
}
