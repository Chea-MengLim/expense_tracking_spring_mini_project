package org.example.kps_group_01_spring_mini_project.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.dto.response.CategoryResponse;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Expense {
    private String expense_id;
    private String amount;
    private String description;
    private String date;
    private User user;
    private CategoryResponse categoryResponse;
}
