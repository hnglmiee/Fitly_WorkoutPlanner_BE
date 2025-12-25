package com.workoutplanner.MiniProject.Controllers;

import com.nimbusds.jose.JOSEException;
import com.workoutplanner.MiniProject.Constants.ApiPaths;
import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Payload.Request.AuthenticationRequest;
import com.workoutplanner.MiniProject.Payload.Request.InstrospectTokenRequest;
import com.workoutplanner.MiniProject.Payload.Request.LogoutRequest;
import com.workoutplanner.MiniProject.Payload.Request.RefreshRequest;
import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import com.workoutplanner.MiniProject.Payload.Response.AuthenticationResponse;
import com.workoutplanner.MiniProject.Payload.Response.IntrospectTokenResponse;
import com.workoutplanner.MiniProject.Services.Implementations.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping(ApiPaths.Auth.ROOT)
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        var result = authenticationService.authenticated(request);
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
        response.setMessage("Login successful!");
        response.setResult(result);
        return response;
    }

//    @PostMapping("/logout")
//    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
//        authenticationService.logout(request);
//        ApiResponse<Void> response = new ApiResponse<>();
//        response.setMessage("Logout successful");
//        return response;
//    }

    @PostMapping("/logout")
    public ApiResponse<Void> logout(HttpServletRequest request)
            throws ParseException, JOSEException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        String token = authHeader.substring(7); // remove "Bearer "
        authenticationService.logout(new LogoutRequest(token));
        ApiResponse<Void> response = new ApiResponse<>();
        response.setMessage("Logout successful");
        return response;
    }


    // Check token hop le
    @PostMapping("/introspect")
    public ApiResponse<IntrospectTokenResponse> introspect(@RequestBody InstrospectTokenRequest request) throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        ApiResponse<IntrospectTokenResponse> response = new ApiResponse<>();
        response.setResult(result);
        return response;
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationService.refreshToken(request);
        ApiResponse<AuthenticationResponse> response = new ApiResponse<>();
        response.setMessage("Refresh token successful!");
        response.setResult(result);
        return response;
    }
}