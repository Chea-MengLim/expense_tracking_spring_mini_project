package org.example.kps_group_01_spring_mini_project.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Otp {
    UUID otpId;
    String otpCode;
    LocalDateTime issuedAt;
    Timestamp expiration;
    boolean verify;
    User userId;
}