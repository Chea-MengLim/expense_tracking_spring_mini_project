package org.example.kps_group_01_spring_mini_project.service.impl;

import jakarta.mail.MessagingException;
import org.example.kps_group_01_spring_mini_project.exception.BadRequestException;
import org.example.kps_group_01_spring_mini_project.exception.NotFoundException;
import org.example.kps_group_01_spring_mini_project.model.Otp;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.ForgetPasswordRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.Response.UserResponse;
import org.example.kps_group_01_spring_mini_project.model.User;
import org.example.kps_group_01_spring_mini_project.repository.UserRepository;
import org.example.kps_group_01_spring_mini_project.service.UserService;
import org.example.kps_group_01_spring_mini_project.util.OtpUtil;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OtpUtil otpUtil;
    private final ModelMapper modelMapper;
    private final EmailingService emailingService;
    private final String[] extensions = {".jpg", ".png", ".pdf", "jpeg", ".gif", ".bmp"};

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
        //check if password and confirm password match
        if (userRequest.getPassword().equals(userRequest.getConfirmPassword())) {
            String otp = otpUtil.generateOtp();
            userRequest.setPassword(bCryptPasswordEncoder.encode(
                    userRequest.getPassword()
            ));
            // check email not duplicate
            List<String> emailList = userRepository.getAllEmail();
            if(emailList.contains(userRequest.getEmail()))
                throw new BadRequestException("This email is already registered");

            // check image format
            boolean isValidProfileImageFormat = false;
            for(String extension : extensions){
                if(userRequest.getProfileImage().contains(extension)){
                    isValidProfileImageFormat = true;
                    break;
                }
            }
            if(!isValidProfileImageFormat){
                throw new BadRequestException("Your profile image format is not supported");
            }
            // insert to table users
            User user = userRepository.register(userRequest);
            try {
                // expiration of OTP code is 1 minute long (now + 1 minutes)
                userRepository.insertUserOtp(otp, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(1)), false, user.getUserId());
                emailingService.sendMail(user.getEmail(), otp);
            } catch (MessagingException e) {
                throw new RuntimeException("Unable to send OTP code.");
            }
            return modelMapper.map(userRepository.findByEmail(user.getEmail()), UserResponse.class);
        }
        else
            throw new BadRequestException("Your confirm password does not match with your password");
    }

    @Override
    public String verify(String otp) {
        Otp userOtp = userRepository.findOtpByOtpCode(otp);
        if (userOtp == null)
            throw new BadRequestException("Your OTP code does not match");
        if (userOtp.getOtpCode().equals(otp)) {
            if(Duration.between(userOtp.getIssuedAt(), LocalDateTime.now()).getSeconds() < (60)){
                userRepository.verifyUserOtpByOtpCode(otp, true);
                return "Your account is successfully verified";
            }
            else
                throw new BadRequestException("Your OTP code has expired");

        }
        else {
            throw new BadRequestException("Your OTP code does not match");
        }
    }

    @Override
    public String resend(String email) {
        String otp = otpUtil.generateOtp();
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new NotFoundException("User does not exist");
        try {
            emailingService.sendMail(email, otp);
            userRepository.insertUserOtp(otp, Timestamp.valueOf(LocalDateTime.now()), Timestamp.valueOf(LocalDateTime.now().plusMinutes(1)), false, user.getUserId());
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

    @Override
    public String forgetPassword(String email, ForgetPasswordRequest forgetPasswordRequest) {
        // check email exist or not
        User user = userRepository.findByEmail(email);
        if(user == null)
            throw new NotFoundException("User does not exist");
        // check if password and confirm password do not matched
        if(!forgetPasswordRequest.getPassword().equals(forgetPasswordRequest.getConfirmPassword())){
            throw new BadRequestException("Your password and Confirm password do not matched");
        }

        // check account is verify or not
        Otp otp = userRepository.getOtpByUserId(user.getUserId());
        if(!otp.isVerify()){
            throw new BadRequestException("please verify otp first");
        }
        // update password to this user
        forgetPasswordRequest.setPassword(bCryptPasswordEncoder.encode(forgetPasswordRequest.getConfirmPassword()));
        userRepository.updatePassword(email, forgetPasswordRequest);
        return "Your password is reset successful";
    }
}
