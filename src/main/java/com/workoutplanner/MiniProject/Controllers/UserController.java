package com.workoutplanner.MiniProject.Controllers;

import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Payload.Request.UserCreationRequest;
import com.workoutplanner.MiniProject.Payload.Request.UserUpdateRequest;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.UserCreationResponse;
import com.workoutplanner.MiniProject.Services.Implementations.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiPaths.User.ROOT)
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    // @Valid: Cho biet se validate object dua theo rules da dc define trong object
    public ApiResponse<UserCreationResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserCreationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createRequest(request));
        return apiResponse;
    }

    @GetMapping
    public List<UserCreationResponse> getUsers() {
        // SecurityContextHolder là trung tâm lưu trữ thông tin bảo mật (Security Context) của người dùng đang đăng nhập hiện tại trong ứng dụng Spring.
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("EMAIL: " + authentication.getName());

        ApiResponse<List<UserCreationResponse>> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUsers());
        return apiResponse.getResult();
    }

    @GetMapping("/{id}")
    public UserCreationResponse getUserById(@PathVariable Integer id) {
        ApiResponse<UserCreationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getUserById(id));
        return apiResponse.getResult();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserCreationResponse> updateUser(@PathVariable Integer id, @RequestBody @Valid UserUpdateRequest request) {
        ApiResponse<UserCreationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.updateUser(id, request));
        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<UserCreationResponse> deleteUser(@PathVariable Integer id) {
        ApiResponse<UserCreationResponse> apiResponse = new ApiResponse<>();
        userService.deleteUser(id);
        apiResponse.setMessage("Successfully deleted user");
        return apiResponse;
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserCreationResponse> getMyInfo() {
        ApiResponse<UserCreationResponse> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.getMyInfo());
        return apiResponse;
    }
}
