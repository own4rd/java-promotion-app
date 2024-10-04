package br.com.lowlevel.promotion_app.controllers;

import br.com.lowlevel.promotion_app.data.vo.v1.UploadFileResponseVO;
import br.com.lowlevel.promotion_app.services.FileStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Tag(name = "File Endpoints")
@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private final Logger logger = Logger.getLogger(FileController.class.getName());

    @Autowired
    private FileStorageService fileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponseVO uploadFile(@RequestParam("file") MultipartFile file) {
        logger.info("Storing file to disk.");
        var filename = fileStorageService.storeFile(file);
        String fileDownloadURI = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/files/downloadFile/")
                .path(filename)
                .toUriString();
        return new UploadFileResponseVO(filename, fileDownloadURI, file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponseVO> uploadMultipleFile(@RequestParam("files") MultipartFile[] files) {
        logger.info("Storing files to disk.");

        return Arrays.stream(files)
                .map(this::uploadFile)
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{filename:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename, HttpServletRequest request) {
        logger.info("Reading a file on disk.");

        Resource resource = fileStorageService.loadFileAsResource(filename);
        String contentType = "";

        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception e) {
            logger.warning("Error reading file: " + e.getMessage());
        }

        if (contentType.isBlank()) contentType = "application/octet-stream";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attatchment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
