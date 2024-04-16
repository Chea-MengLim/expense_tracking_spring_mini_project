package org.example.kps_group_01_spring_mini_project.model.Request;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailRequest {
    @JsonAlias(value = "to_email")
    private String toEmail;

    private String subject;

    private String message;

    @JsonAlias(value = "html")
    private boolean isHTML;
}
