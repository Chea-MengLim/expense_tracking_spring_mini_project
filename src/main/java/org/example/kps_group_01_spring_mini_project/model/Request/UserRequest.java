package org.example.kps_group_01_spring_mini_project.model.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    String email;
    String password;
    String profileImage;
}
