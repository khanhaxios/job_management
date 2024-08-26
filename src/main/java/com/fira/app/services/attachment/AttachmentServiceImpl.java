package com.fira.app.services.attachment;


import com.fira.app.entities.Attachment;
import com.fira.app.repository.AttachmentRepository;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;

    @Override
    public ResponseEntity<?> store(List<MultipartFile> files, Path path) throws Exception {
        List<Attachment> resultPaths = new ArrayList<>();
        for (MultipartFile file : files) {
            Attachment attachment = new Attachment();
            String filename = file.getOriginalFilename();
            if (filename == null) continue;
            Path filePath = path.resolve(filename);
            Files.copy(file.getInputStream(), filePath);
            attachment.setPath(filename);
        }
        return ResponseHelper.success(attachmentRepository.saveAll(resultPaths));
    }

    @Override
    public ResponseEntity<?> getFile(String path) throws Exception {
        Path file = Paths.get(path);
        Resource resource = new UrlResource(file.toUri());
        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
        } else {
            return ResponseHelper.notFound("Attachment not found by path" + path);
        }
    }

    @Override
    public ResponseEntity<?> getAttachmentById(Long id) {
        return ResponseHelper.success(attachmentRepository.findById(id).orElse(null));
    }
}
