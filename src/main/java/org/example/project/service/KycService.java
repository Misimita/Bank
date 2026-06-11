package org.example.project.service;

import org.example.project.entity.KycProfile;
import org.example.project.entity.enums.KycStatus;
import org.example.project.entity.User;
import org.example.project.repository.KycProfileRepository;
import org.example.project.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class KycService {

    private final UserRepository userRepository;
    private final KycProfileRepository kycProfileRepository;

    public KycService(UserRepository userRepository, KycProfileRepository kycProfileRepository) {
        this.userRepository = userRepository;
        this.kycProfileRepository = kycProfileRepository;
    }

    @Transactional
    public String uploadKyc(Long userId, MultipartFile document) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String fileName = UUID.randomUUID() + "_" + document.getOriginalFilename();
        Path uploadPath = Paths.get("uploads/" + fileName);
        Files.createDirectories(uploadPath.getParent());
        Files.write(uploadPath, document.getBytes());

        KycProfile kyc = new KycProfile();
        kyc.setUser(user);
        kyc.setIdCardFrontUrl("/uploads/" + fileName);
        kyc.setStatus(KycStatus.PENDING);
        kycProfileRepository.save(kyc);

        return "KYC uploaded successfully";
    }
}