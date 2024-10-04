package br.com.lowlevel.promotion_app.services;

import br.com.lowlevel.promotion_app.config.FileStorageConfig;
import br.com.lowlevel.promotion_app.exceptions.FileStorageException;
import br.com.lowlevel.promotion_app.exceptions.MyFileNotFoundExceptionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private final Path fileStorageLocation;

    @Autowired
    public FileStorageService(FileStorageConfig fileStorageConfig) {
        Path path = Paths.get(fileStorageConfig.getUploadDir())
                .toAbsolutePath().normalize();
        this.fileStorageLocation = path;

        // Log the path for debugging
        System.out.println("Upload directory: " + this.fileStorageLocation);

        try {
            // Attempt to create the directory
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            // Log the exception for debugging
            System.err.println("Error creating directory: " + ex.getMessage());
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (filename.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + filename);
            }
            Path targetLocation = this.fileStorageLocation.resolve(filename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store file " + filename + ". Please try again!", ex);
        }
    }


    public Resource loadFileAsResource(String filename) {
        try {
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) return resource;
            else throw new MyFileNotFoundExceptionException("File not found");
        } catch (Exception e) {
            throw new MyFileNotFoundExceptionException("File not found" + filename, e);
        }
    }
}
