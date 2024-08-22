package com.fira.app.services.attachment;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;

public interface AttachmentService {
    ResponseEntity<?> store(List<MultipartFile> files, Path source) throws Exception;

    ResponseEntity<?> getFile(String path) throws Exception;

    ResponseEntity<?> getAttachmentById(Long id);
}
