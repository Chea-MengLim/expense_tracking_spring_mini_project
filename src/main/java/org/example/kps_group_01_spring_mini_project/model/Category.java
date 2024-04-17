package org.example.kps_group_01_spring_mini_project.model;

import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Category {
    private String id;
    private String name;
    private String description;
    private String userId;
}
