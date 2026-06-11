package org.example.project.controller;

import org.example.project.service.KycService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/kyc")
public class KycController {

    private final KycService kycService;

    public KycController(KycService kycService) {
        this.kycService = kycService;
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadKyc(@RequestParam Long userId,
                                       @RequestParam("document") org.springframework.web.multipart.MultipartFile document) throws IOException {
        String result = kycService.uploadKyc(userId, document);
        return ResponseEntity.ok(result);
    }
}