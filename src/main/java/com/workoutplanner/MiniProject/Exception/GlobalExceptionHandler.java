package com.workoutplanner.MiniProject.Exception;

import com.workoutplanner.MiniProject.Payload.Response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// Bắt các lỗi ném ra trong toàn bộ hệ thống, trả về phản hồi JSON chuẩn cho client.
// Bắt mọi lỗi xảy ra ở controller/service, tránh crash app.
// Trả về phản hồi JSON đẹp cho client.
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = RuntimeException.class)
    ResponseEntity<ApiResponse> handlingRuntimeException(RuntimeException exception) {
        ApiResponse apiResponse = new ApiResponse();

//        apiResponse.setCode(9999); // Hard code, ko nên --> Tao enum ErrorCode
        apiResponse.setCode(ErrorCode.UNCATEGORIZED.getCode());
        apiResponse.setMessage(ErrorCode.UNCATEGORIZED.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorCode.getCode()); // Hard code
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
        ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        String enumKey = exception.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.valueOf(enumKey);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }
}
