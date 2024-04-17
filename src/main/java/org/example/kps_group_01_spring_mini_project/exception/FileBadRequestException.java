package org.example.kps_group_01_spring_mini_project.exception;

public class FileBadRequestException extends RuntimeException{
    public FileBadRequestException(String message) {
        super(message);
    }
}
