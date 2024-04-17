package org.example.kps_group_01_spring_mini_project.model.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    UUID userId;
    String email;
    String profileImage;
}
