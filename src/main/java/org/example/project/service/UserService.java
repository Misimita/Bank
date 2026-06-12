package org.example.project.service;

import org.example.project.dto.UserResponseDto;
import org.example.project.entity.User;
import org.example.project.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserResponseDto> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // Sử dụng projection để lấy thêm accountNumber và balance
        Page<UserResponseDto> usersPage = userRepository.findAllUsers(pageable);

        return usersPage;
    }

    @Transactional
    public void changePin(Long userId, String newPin) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // TODO: Mã hóa PIN bằng BCrypt theo SRS (strength 10)
        // user.setPin(bCryptPasswordEncoder.encode(newPin));
        user.setPin(newPin); // tạm thời
        userRepository.save(user);
    }
}