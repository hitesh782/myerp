package com.example.erpdemo.rest;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.erpdemo.model.MonitoringUrl;
import com.example.erpdemo.service.MonitoringService;

@RestController
@RequestMapping("/monitoring")
public class MonitoringController {

    @Autowired
    private MonitoringService monitoringService;

    @GetMapping("/urls")
    public List<MonitoringUrl> getAllMonitoringUrls() {
        return monitoringService.getAllMonitoringUrls();
    }

    @PostMapping("/url")
    public MonitoringUrl addMonitoringUrl(@RequestBody MonitoringUrl monitoringUrl) {
        return monitoringService.saveMonitoringUrl(monitoringUrl);
    }

    @PutMapping("/url")
    public MonitoringUrl updateMonitoringUrl(@RequestBody MonitoringUrl monitoringUrl) {
        return monitoringService.saveMonitoringUrl(monitoringUrl);
    }

    @DeleteMapping("url/{id}")
    public void deleteMonitoringUrl(@PathVariable String id) {
        monitoringService.deleteMonitoringUrl(id);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("file is empty");
        }

        try {
            String uploadPath = "D:\\dummy_folder";
            File directory = new File(uploadPath);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // save the file to the specified directory
            File destinationFile = new File(uploadPath + File.separator + file.getOriginalFilename());
            file.transferTo(destinationFile);
            return ResponseEntity.ok("File uploaded successfully: " + destinationFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("File upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            String uploadPath = "D:\\dummy_folder";
            File file = new File(uploadPath + File.separator + filename);
            if (!file.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            Resource resource = new UrlResource(file.toURI());
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(Files.probeContentType(file.toPath())))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"")
                    .body(resource);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
