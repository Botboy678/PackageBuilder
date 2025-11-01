package com.AlansIdea.PackageBuilder2.Controllers;

import com.AlansIdea.PackageBuilder2.Configs.S3Configs.S3Bucket;
import com.AlansIdea.PackageBuilder2.Services.S3Service;
import com.AlansIdea.PackageBuilder2.Services.TchartService;
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

    private final TchartService tchartService;

    public TestingController(TchartService tchartService, S3Service s3Service, S3Bucket s3Buckets) {
        this.tchartService = tchartService;
    }

    @PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testUploads(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tchartService.UploadPreFilledTchart(file));
    }
}

