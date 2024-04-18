package org.example.kps_group_01_spring_mini_project.controller;

import lombok.AllArgsConstructor;
import org.example.kps_group_01_spring_mini_project.model.FileResponse;
import org.example.kps_group_01_spring_mini_project.service.FileService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("api/v1/files")
@AllArgsConstructor
public class FileController {
    private final FileService fileService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResponse uploadFile(@RequestParam MultipartFile file) throws IOException {
        FileResponse fileResponse;
        String fileName = fileService.saveFile(file);
        fileResponse = new FileResponse(fileName, file.getContentType(), file.getSize());

        return fileResponse;
    }

    @GetMapping
    public ResponseEntity<?> getFile(@RequestParam String fileName) throws IOException {
        ByteArrayResource resource = fileService.getFileByFileName(fileName);
        MediaType mediaType;
        if (fileName.endsWith(".pdf")){mediaType = MediaType.APPLICATION_PDF;}
        else if(fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif")){
            mediaType = MediaType.IMAGE_PNG;}
        else {mediaType = MediaType.APPLICATION_OCTET_STREAM;}
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"")
                .contentType(mediaType)
                .body(resource);
    }

}
