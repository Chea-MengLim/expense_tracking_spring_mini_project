package org.example.kps_group_01_spring_mini_project.service.impl;

import org.example.kps_group_01_spring_mini_project.exception.FileBadRequestException;
import org.example.kps_group_01_spring_mini_project.exception.FileNotFoundException;
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
        assert type != null;

        switch (type) {
            case "image/png":
            case "image/jpg":
            case "image/jpeg":
                fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                Files.copy(file.getInputStream(), path.resolve(fileName));
                break;
            default:
                throw new FileBadRequestException("File must be contain jpg, png, jpeg");

        }
        return fileName;
    }
    @Override
    public ByteArrayResource getFileByFileName(String fileName) throws IOException {
        //get file path
        Path path = Paths.get("src/main/resources/files/" + fileName);
        if (!path.toFile().isFile()) {
            throw new FileNotFoundException("File not founded");
        }

        //read file as byte
        return new ByteArrayResource(Files.readAllBytes(path));
    }
}