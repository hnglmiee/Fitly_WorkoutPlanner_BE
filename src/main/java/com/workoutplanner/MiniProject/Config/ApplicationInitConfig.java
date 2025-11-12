package com.workoutplanner.MiniProject.Config;

import com.workoutplanner.MiniProject.Models.Role;
import com.workoutplanner.MiniProject.Models.User;
import com.workoutplanner.MiniProject.Repositories.RoleRepository;
import com.workoutplanner.MiniProject.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@Slf4j
public class ApplicationInitConfig {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository, RoleRepository roleRepository) {
        return args -> {
            // Kiểm tra xem user "Admin" đã tồn tại chưa, rồi thì ko thêm nữa
            if (userRepository.findByFullName("Admin").isEmpty()) {

                // Lấy role ADMIN từ DB
                Role adminRole = roleRepository.findByRoleName("ADMIN")
                        .orElseThrow(() -> new RuntimeException("Role ADMIN not found!"));

                // Tạo user mới
                User user = new User();
                user.setFullName("Admin");
                user.setEmail("admin@example.com");
                user.setPasswordHash(passwordEncoder.encode("admin"));
                user.setRole(adminRole);

                // Lưu user vào DB
                userRepository.save(user);

                System.out.println("Admin user created successfully with default password: admin.");
            }
        };
    }
}
