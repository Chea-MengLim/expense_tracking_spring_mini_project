package org.example.kps_group_01_spring_mini_project.service;

import jakarta.mail.MessagingException;
import org.example.kps_group_01_spring_mini_project.model.Otp;
import org.example.kps_group_01_spring_mini_project.model.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.model.Response.UserResponse;
import org.example.kps_group_01_spring_mini_project.model.User;
import org.example.kps_group_01_spring_mini_project.repository.UserRepository;
import org.example.kps_group_01_spring_mini_project.util.OtpUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OtpUtil otpUtil;
    private final ModelMapper modelMapper;
    private final EmailingService emailingService;
    private UUID handleUserId;

    public UserServiceImplement(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OtpUtil otpUtil, ModelMapper modelMapper, EmailingService emailingService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpUtil = otpUtil;
        this.modelMapper = modelMapper;
        this.emailingService = emailingService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserResponse register(UserRequest userRequest) {
        String otp = otpUtil.generateOtp();
        userRequest.setPassword(bCryptPasswordEncoder.encode(
                userRequest.getPassword()
        ));
        User user = userRepository.register(userRequest);
        handleUserId = user.getUserId();
        System.out.println("here is user id " + user.getUserId());
        try {
            userRepository.insertUserOtp(otp, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf("2024-04-14 21:58:58.000000"), false, user.getUserId());
            emailingService.sendMail(user.getEmail(), otp);
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send OTP code.");
        }
        return modelMapper.map(userRepository.findByEmail(user.getEmail()), UserResponse.class);
    }

    @Override
    public String verify(String otp) {
        Otp userOtp = userRepository.findOtpByUserId(handleUserId);
        if (userOtp.getOtpCode().equals(otp) && Duration.between(userOtp.getIssuedAt(), LocalDateTime.now()).getSeconds() < (1  * 60)) {
            userRepository.verifyUserOtp(handleUserId, true);
            return "Successfully verify.";
        }
        return "Something error please verify again.";
    }

    @Override
    public String resend(String email) {
        String otp = otpUtil.generateOtp();
        User user = userRepository.findByEmail(email);
        try {
            emailingService.sendMail(email, otp);
            userRepository.resendVerify(user.getUserId(), otp, LocalDateTime.now());
        } catch (MessagingException e) {
            throw new RuntimeException("Unable to send OTP code.");
        }
        return "Successfully send new OTP code.";
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public Otp getOtpByUserId(UUID id) {
        return userRepository.getOtpByUserId(id);
    }
}
