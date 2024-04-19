package org.example.kps_group_01_spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String email;
    private String profileImage;

}
