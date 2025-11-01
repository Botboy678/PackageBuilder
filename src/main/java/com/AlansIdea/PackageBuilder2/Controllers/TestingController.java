package com.AlansIdea.PackageBuilder2.Controllers;

import com.AlansIdea.PackageBuilder2.Configs.S3Bucket;
import com.AlansIdea.PackageBuilder2.Services.S3Service;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/TestBucket")
public class TestingController {

    private final S3Service s3Service;
    private final S3Bucket s3Buckets;

    public TestingController(S3Service s3Service, S3Bucket s3Buckets) {
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testUploads(@RequestParam("file") MultipartFile file) {
        String key = "Tcharts/" + UUID.randomUUID();

        try {
            s3Service.putObject(s3Buckets.getTchartBucket(), key, file.getBytes());
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body("Success! Your bucket is set up and file uploaded.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to upload file: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }
}

