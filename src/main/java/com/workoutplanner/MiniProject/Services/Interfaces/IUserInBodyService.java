package com.workoutplanner.MiniProject.Services.Interfaces;

import com.workoutplanner.MiniProject.Models.UserInbody;
import com.workoutplanner.MiniProject.Payload.Response.UserInbodyResponse;

import java.util.List;

public interface IUserInBodyService {
    List<UserInbodyResponse> getMyInBody();
}
