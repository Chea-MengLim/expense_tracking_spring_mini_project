package org.example.kps_group_01_spring_mini_project.Model.Dto.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    Integer userId;
    String email;
    String password;
    String profileImage;
}
