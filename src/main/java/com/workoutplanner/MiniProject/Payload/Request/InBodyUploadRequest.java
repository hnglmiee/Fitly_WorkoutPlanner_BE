package com.workoutplanner.MiniProject.Payload.Request;

import org.springframework.web.multipart.MultipartFile;

// DTO nhận file PDF từ client
public class InBodyUploadRequest {
    private MultipartFile file;
    private Integer userId;

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
