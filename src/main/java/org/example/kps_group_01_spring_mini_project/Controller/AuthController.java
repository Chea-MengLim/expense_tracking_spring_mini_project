package org.example.kps_group_01_spring_mini_project.Controller;

import org.apache.coyote.BadRequestException;
import org.apache.ibatis.javassist.NotFoundException;
import org.example.kps_group_01_spring_mini_project.Jwt.JwtService;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Request.AuthRequest;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Response.AuthResponse;
import org.example.kps_group_01_spring_mini_project.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
            if (userApp == null){throw new BadRequestException("Wrong Email");}
            if (!passwordEncoder.matches(password, userApp.getPassword())){
                throw new BadRequestException("Wrong Password");}
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);} catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);}}

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        authenticate(authRequest.getEmail(), authRequest.getPassword());
        final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
        final String token = jwtService.generateToken(userDetails);
        AuthResponse authResponse = new AuthResponse(token);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        return ResponseEntity.ok(userService.register(userRequest));
    }
//
//    @PutMapping("/verify")
//    public ResponseEntity<String> verifyAccount(@RequestParam String otp) {
//        return new ResponseEntity<>(appUserService.verify(otp), HttpStatus.OK);
//    }
//
//    @PostMapping("/resend")
//    public ResponseEntity<String> resendOtp(@RequestParam String email) {
//        return new ResponseEntity<>(appUserService.resend(email), HttpStatus.OK);
//    }
}
