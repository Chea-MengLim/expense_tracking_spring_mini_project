package org.example.kps_group_01_spring_mini_project.Service;

import org.example.kps_group_01_spring_mini_project.Model.Dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Response.UserResponse;
import org.example.kps_group_01_spring_mini_project.Model.Dto.User;
import org.example.kps_group_01_spring_mini_project.Repository.UserRepository;
import org.example.kps_group_01_spring_mini_project.Util.OtpUtil;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplement implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final OtpUtil otpUtil;
    private final ModelMapper modelMapper;

    public UserServiceImplement(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, OtpUtil otpUtil, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.otpUtil = otpUtil;
        this.modelMapper = modelMapper;
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
        return modelMapper.map(userRepository.findByEmail(user.getEmail()), UserResponse.class);
    }
}
