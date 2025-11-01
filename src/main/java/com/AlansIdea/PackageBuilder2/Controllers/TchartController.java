package com.AlansIdea.PackageBuilder2.Controllers;

import com.AlansIdea.PackageBuilder2.Configs.S3Configs.S3Bucket;
import com.AlansIdea.PackageBuilder2.Services.S3Service;
import com.AlansIdea.PackageBuilder2.Services.TchartService;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.slf4j.Logger;

@RestController
@RequestMapping("/TestBucket")
public class TchartController {

    private final TchartService tchartService;

    private final static Logger logger = LoggerFactory.getLogger(TchartController.class);
    public TchartController(TchartService tchartService, S3Service s3Service, S3Bucket s3Buckets) {
        this.tchartService = tchartService;
    }

    @PutMapping(value = "/Upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImageAndGetText(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tchartService.readTextFromTchart(file));
    }

}

