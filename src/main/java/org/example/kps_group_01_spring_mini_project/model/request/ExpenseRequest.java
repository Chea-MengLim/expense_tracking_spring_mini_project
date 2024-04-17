package org.example.kps_group_01_spring_mini_project.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    private Integer amount;
    private String description;
    private LocalDateTime date;
    private UUID category_id;
}
