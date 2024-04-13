package org.example.kps_group_01_spring_mini_project.service.impl;

import org.example.kps_group_01_spring_mini_project.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private final Path path = Paths.get("src/main/resources/files");
    @Override
    public String saveFile(MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String type = file.getContentType();
        assert fileName != null;

        switch (type) {
            case "image/png":
            case "image/jpg":
            case "image/jpeg":
            case "application/pdf":
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet":
            case "application/vnd.openxmlformats-officedocument.presentationml.presentation":
            case "video/mp4":
            case "video/mpeg":
                fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                Files.copy(file.getInputStream(), path.resolve(fileName));
                break;
            default:
                return "File format not supported";

        }
        return fileName;
    }
    @Override
    public ByteArrayResource getFileByFileName(String fileName) throws IOException {
        //get file path
        Path path = Paths.get("src/main/resources/files/" + fileName);
        //read file as byte
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}
