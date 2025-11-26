package com.workoutplanner.MiniProject.Services.Implementations;

import com.workoutplanner.MiniProject.Exception.AppException;
import com.workoutplanner.MiniProject.Exception.ErrorCode;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Models.UserInbody;
import com.workoutplanner.MiniProject.Payload.Response.UserInbodyResponse;
import com.workoutplanner.MiniProject.Repositories.UserInbodyRepository;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import com.workoutplanner.MiniProject.Repositories.WorkoutScheduleRepository;
import com.workoutplanner.MiniProject.Services.Interfaces.IUserInBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserInBodyService implements IUserInBodyService {
    @Autowired
    private UserInbodyRepository userInbodyRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WorkoutScheduleRepository workoutScheduleRepository;

    public UserInBodyService(UserInbodyRepository userInbodyRepository, UserRepository userRepository) {
        this.userInbodyRepository = userInbodyRepository;
        this.userRepository = userRepository;
    }
    @Override
    public List<UserInbodyResponse> getMyInBody() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        // Lấy danh sách InBody và sort theo ngày
        List<UserInbody> userInbodyList = userInbodyRepository.findByUser(user);
        userInbodyList.sort((a, b) -> a.getMeasuredAt().compareTo(b.getMeasuredAt()));

        List<UserInbodyResponse> userInbodyResponses = new ArrayList<>();
        BigDecimal previousBodyFat = null;
        BigDecimal previousMuscleMass = null;

        for (UserInbody userInbody : userInbodyList) {
            UserInbodyResponse response = new UserInbodyResponse();
            response.setId(userInbody.getId());
            response.setFullName(userInbody.getUser().getFullName());
            response.setMeasuredAt(userInbody.getMeasuredAt());
            response.setHeight(userInbody.getHeight());
            response.setWeight(userInbody.getWeight());
            response.setBodyFatPercentage(userInbody.getBodyFatPercentage());
            response.setMuscleMass(userInbody.getMuscleMass());

            // Tính BMI & Lean Body Mass từ số đo đã có
            BigDecimal heightBD = userInbody.getHeight();
            BigDecimal weightBD = userInbody.getWeight();
            BigDecimal bodyFat = userInbody.getBodyFatPercentage();

            BigDecimal bmi = null;
            BigDecimal leanBodyMass = null;

            if (heightBD != null && weightBD != null) {
                double height = heightBD.doubleValue() / 100.0;
                double weight = weightBD.doubleValue();
                bmi = BigDecimal.valueOf(weight / (height * height)).setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            if (weightBD != null && bodyFat != null) {
                leanBodyMass = BigDecimal.valueOf(weightBD.doubleValue() * (1 - bodyFat.doubleValue() / 100))
                        .setScale(2, BigDecimal.ROUND_HALF_UP);
            }

            response.setBmi(bmi);
            response.setLeanBodyMass(leanBodyMass);

            // Trend tăng/giảm Body Fat & Muscle Mass
            if (previousBodyFat != null) {
                response.setBodyFatTrend(bodyFat.compareTo(previousBodyFat) > 0 ? "up" :
                        bodyFat.compareTo(previousBodyFat) < 0 ? "down" : "stable");
            } else response.setBodyFatTrend("stable");

            if (previousMuscleMass != null) {
                response.setMuscleMassTrend(userInbody.getMuscleMass().compareTo(previousMuscleMass) > 0 ? "up" :
                        userInbody.getMuscleMass().compareTo(previousMuscleMass) < 0 ? "down" : "stable");
            } else response.setMuscleMassTrend("stable");

            previousBodyFat = bodyFat;
            previousMuscleMass = userInbody.getMuscleMass();

            response.setNotes(userInbody.getNotes());
            userInbodyResponses.add(response);
        }

        return userInbodyResponses;
    }
}
