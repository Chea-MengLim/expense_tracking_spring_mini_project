package org.example.kps_group_01_spring_mini_project.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.kps_group_01_spring_mini_project.model.response.UserResponse;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
    private String categoryId;
    private String name;
    private String description;
}
