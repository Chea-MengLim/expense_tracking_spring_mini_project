package org.example.kps_group_01_spring_mini_project.Service;

import org.example.kps_group_01_spring_mini_project.Model.Dto.Request.UserRequest;
import org.example.kps_group_01_spring_mini_project.Model.Dto.Response.UserResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
    UserDetails loadUserByUsername(String email);

    UserResponse register(UserRequest userRequest);
}
