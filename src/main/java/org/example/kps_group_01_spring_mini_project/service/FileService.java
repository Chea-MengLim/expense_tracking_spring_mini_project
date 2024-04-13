package org.example.kps_group_01_spring_mini_project.service;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    String saveFile(MultipartFile file) throws IOException;

    ByteArrayResource getFileByFileName(String fileName) throws IOException;
}
