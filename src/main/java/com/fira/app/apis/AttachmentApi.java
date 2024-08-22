package com.fira.app.apis;

import com.fira.app.services.attachment.AttachmentService;
import com.fira.app.utils.ResponseHelper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.SystemProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/attachments")
@CrossOrigin
public class AttachmentApi {

    private final AttachmentService attachmentService;
    Path path = Paths.get(System.getProperty("user.dir"), "uploads");

    @PostConstruct
    public void init() throws IOException {
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }
    }

    @PostMapping
    public ResponseEntity<?> uploadAttachment(@RequestParam(name = "attachments") List<MultipartFile> multipartFiles) {
        try {
            return attachmentService.store(multipartFiles, path);
        } catch (Exception e) {
            return ResponseHelper.serverError(e.getMessage());
        }
    }
}
