package org.example.kps_group_01_spring_mini_project.service;

import org.example.kps_group_01_spring_mini_project.model.Otp;
import org.example.kps_group_01_spring_mini_project.model.dto.request.ForgetPasswordRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.request.UserRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.response.UserResponse;
import org.example.kps_group_01_spring_mini_project.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);

    UserResponse register(UserRequest userRequest);

    String verify(String otp);

    String resend(String email);
    User getUserByEmail(String email);
    Otp getOtpByUserId(UUID id);

    String forgetPassword(String email, ForgetPasswordRequest forgetPasswordRequest);
}
