package com.AlansIdea.PackageBuilder2.Services;


import com.AlansIdea.PackageBuilder2.Configs.S3Configs.S3Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;


@Service
public class TchartService {
    private final static Logger logger = LoggerFactory.getLogger(TchartService.class);
    private final S3Service s3Service;
    private final S3Bucket s3Buckets;



    public TchartService(S3Service s3Service, S3Bucket s3Buckets) {
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    public String UploadPreFilledTchart(MultipartFile file){
        String key = "TCharts/Prefilled" + UUID.randomUUID();

        try {
            s3Service.putObject(s3Buckets.getTchartBucket(), key, file.getBytes());
            logger.info("Success! Your bucket is set up and file uploaded.");
        } catch (IOException e) {
            logger.error("Failed to upload file: {}", e.getMessage());
            return "Failed to upload file";
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        }
        return "Success! Your bucket is set up and file uploaded.";
    }
}
