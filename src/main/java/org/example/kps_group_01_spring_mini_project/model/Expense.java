package org.example.kps_group_01_spring_mini_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private String expense_id;
    private Integer amount;
    private String description;
    private LocalDateTime date;
    private String user_id;
    private String category_id;
}
