package org.example.kps_group_01_spring_mini_project.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse<T> {
    private String message;
    private T payload;
    private HttpStatus status;
    private LocalDateTime dateTime;
}