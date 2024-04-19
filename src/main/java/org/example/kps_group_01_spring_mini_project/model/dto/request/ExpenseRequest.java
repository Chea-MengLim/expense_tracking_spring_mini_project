package org.example.kps_group_01_spring_mini_project.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpenseRequest {
    @Positive
    private Integer amount;
    @NotNull
    @NotBlank
    private String description;
    private LocalDateTime date;
    @NotNull
    @NotBlank
    private String category_id;
}
