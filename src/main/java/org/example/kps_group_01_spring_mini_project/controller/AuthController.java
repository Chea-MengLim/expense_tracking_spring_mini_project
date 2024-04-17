package org.example.kps_group_01_spring_mini_project.controller;

import jakarta.validation.Valid;
import org.example.kps_group_01_spring_mini_project.jwt.JwtService;
import org.example.kps_group_01_spring_mini_project.model.Otp;
import org.example.kps_group_01_spring_mini_project.model.User;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.AuthRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.ForgetPasswordRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.model.dto.Response.AuthResponse;
import org.example.kps_group_01_spring_mini_project.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;
import org.example.kps_group_01_spring_mini_project.exception.BadRequestException;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(BCryptPasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            UserDetails userApp = userService.loadUserByUsername(username);
            if (userApp == null) {
                throw new BadRequestException("Wrong Email");
            }
            if (!passwordEncoder.matches(password, userApp.getPassword())){
                throw new BadRequestException("Wrong Password");}
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);} catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);}}

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody @Valid AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);

        //logic for user if verify show token if not gg
        String userEmail = userDetails.getUsername();
        User returnUser = userService.getUserByEmail(userEmail);
        UUID uId = returnUser.getUserId();
        Otp returnOtp = userService.getOtpByUserId(uId);
        if (!returnOtp.isVerify()) {
            return ResponseEntity.ok("Please verify OTP first.");
        }
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userRequest));
    }

    @PutMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String otp) {
        return new ResponseEntity<>(userService.verify(otp), HttpStatus.OK);
    }

    @PostMapping("/resend")
    public ResponseEntity<String> resendOtp(@RequestParam String email) {
        return new ResponseEntity<>(userService.resend(email), HttpStatus.OK);
    }

    @PutMapping("/forget")
    public ResponseEntity<String> forgetPassword(@RequestParam String email, @RequestBody @Valid ForgetPasswordRequest forgetPasswordRequest){
        return new ResponseEntity<>(userService.forgetPassword(email, forgetPasswordRequest), HttpStatus.OK);
    }
}
