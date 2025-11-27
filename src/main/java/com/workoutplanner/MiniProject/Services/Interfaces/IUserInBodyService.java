package com.workoutplanner.MiniProject.Services.Interfaces;

import com.workoutplanner.MiniProject.Payload.Request.UserInBodyUpdateRequest;
import com.workoutplanner.MiniProject.Payload.Response.UserInBodyUpdateResponse;
import com.workoutplanner.MiniProject.Payload.Response.UserInbodyResponse;

import java.util.List;

public interface IUserInBodyService {
    List<UserInbodyResponse> getMyInBody();
    UserInBodyUpdateResponse updateUserInBody(Integer id, UserInBodyUpdateRequest request);
    boolean deleteUserInBody(Integer id);
}
