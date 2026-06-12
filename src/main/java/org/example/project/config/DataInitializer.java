package org.example.project.config;

import org.example.project.entity.User;
import org.example.project.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Tạo Admin
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123"));
                admin.setFullName("Administrator");
                admin.setEmail("admin@rikkei.bank");
                admin.setPhone("0123456789");
                admin.setPin("0000");
                admin.setRole("ADMIN");
                admin.setKyc(true);
                userRepository.save(admin);
                System.out.println("✅ Đã tạo tài khoản ADMIN: admin / admin123");
            }

            // Tạo Staff
            if (!userRepository.existsByUsername("staff")) {
                User staff = new User();
                staff.setUsername("staff");
                staff.setPassword(passwordEncoder.encode("staff123"));
                staff.setFullName("Giao dịch viên");
                staff.setEmail("staff@rikkei.bank");
                staff.setPhone("0987654321");
                staff.setPin("1111");
                staff.setRole("STAFF");
                staff.setKyc(true);
                userRepository.save(staff);
                System.out.println("Đã tạo tài khoản STAFF: staff / staff123");
            }
        };
    }
}