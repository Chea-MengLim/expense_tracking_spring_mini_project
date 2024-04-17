package org.example.kps_group_01_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private Integer amount;
    private String description;
    private LocalDateTime date;
    private Integer category_id;
}
