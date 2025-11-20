package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.Role;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Payload.Request.UserCreationRequest;
import com.workoutplanner.MiniProject.Payload.Request.UserUpdateRequest;
import com.workoutplanner.MiniProject.Payload.Response.UserCreationResponse;
import com.workoutplanner.MiniProject.Repositories.RoleRepository;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private WebMvcConfigurer openEntityManagerInViewInterceptorConfigurer;

    public UserCreationResponse createRequest(UserCreationRequest request) {
        // Tao user moi
        User user = new User();

        // Neu email bi trung -> Bao loi
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }

        // Set gia tri cho user vua tao
        // DTO -> Entity
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
//        user.setPasswordHash(request.getPasswordHash());

        // Enable BCrypt
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        // Ma hoa password
        user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));

        user.setPhoneNumber(request.getPhoneNumber());

        // Tim USER trong Role Repository
        Role defaultRole = roleRepository.findByRoleName("USER").orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        // Gan default cho User
        user.setRole(defaultRole);

        user.setGender(request.getGender());
        user.setBirthday(request.getBirthday());

        // Luu User vao DB
        // Entity -> DTO
//        User savedUser = userRepository.save(user);
        UserCreationResponse savedUser = new UserCreationResponse(userRepository.save(user));
        // Tra ve DTO response
        return savedUser;
    }


//    @PreAuthorize("hasRole('ADMIN')")
//    Kiem tra truoc luc goi vao method
    public List<UserCreationResponse> getUsers() {
        // Lay User tu DB
        List<User> users = userRepository.findAll();

        // Biến danh sách users (List<User>) thành một Stream<User>,
        // .collect(Collectors.toList()) -> Thu kết quả (từ Stream) về lại thành List<UserCreationResponse>.

        // Chuyen Entity -> DTO
        return users.stream()
                .map(UserCreationResponse::new)
                .collect(Collectors.toList());
    }

//    @PostAuthorize("hasRole('ADMIN')")
//    @PostAuthorize("returnObject.fullName == authentication.name") -> Check fullname tra ve = voi fullname nhap vao
//    Thuc hien method xong roi moi check -> Co quyen moi tra ve
    public UserCreationResponse getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new UserCreationResponse(user);
    }

    public UserCreationResponse updateUser(Integer userId, UserUpdateRequest request) {
        // Find user by id
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Update
        if(request.getFullName() != null) {
            user.setFullName(request.getFullName());
        } if(request.getEmail() != null) {
            // Trung email
            if(userRepository.existsByEmail(request.getEmail()) && !request.getEmail().equals(user.getEmail())) {
                throw new AppException(ErrorCode.USER_EXISTED);
            };
            user.setEmail(request.getEmail());

        } if(request.getPhoneNumber() != null) {
            user.setPhoneNumber(request.getPhoneNumber());

        } if(request.getPasswordHash() != null) {
//            user.setPasswordHash(request.getPasswordHash());
//            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
            user.setPasswordHash(passwordEncoder.encode(request.getPasswordHash()));
        } if(request.getGender() != null) {
            user.setGender(request.getGender());
        } if(request.getBirthday() != null) {
            user.setBirthday(request.getBirthday());
        }

        User updatedUser = userRepository.save(user);
        return new UserCreationResponse(updatedUser);
    }

    public boolean deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
        return true;
    }

    public UserCreationResponse getMyInfo() {
        var context = SecurityContextHolder.getContext(); // Lay thong tin user hien tai
        String email = context.getAuthentication().getName(); // Name cua user da duoc dang nhap
        User user = userRepository.findByEmail(email).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return new UserCreationResponse(user);
    }
}
