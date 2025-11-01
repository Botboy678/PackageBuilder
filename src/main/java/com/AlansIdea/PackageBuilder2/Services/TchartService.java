package com.AlansIdea.PackageBuilder2.Services;


import com.AlansIdea.PackageBuilder2.Configs.S3Configs.S3Bucket;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;


@Service
public class TchartService {
    @Value("${tesseract.datapath}")
    private String tessDataPath;

    private final static Logger logger = LoggerFactory.getLogger(TchartService.class);
    private final S3Service s3Service;
    private final S3Bucket s3Buckets;
    private final Tesseract tesseract;



    public TchartService(S3Service s3Service, S3Bucket s3Buckets, Tesseract tesseract) {
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
        this.tesseract = tesseract;
    }

    public String readTextFromTchart(MultipartFile file) {
        String key = "TCharts/Prefilled/" + file.getOriginalFilename();
        logger.info("Starting T-chart OCR process for file: {}", file.getOriginalFilename());

        // Upload file to S3
        try {
            logger.debug("Uploading T-chart to S3 bucket: {}", s3Buckets.getTchartBucket());
            s3Service.putObject(s3Buckets.getTchartBucket(), key, file.getBytes());
            logger.info("Successfully uploaded file '{}' to bucket '{}'", key, s3Buckets.getTchartBucket());
        } catch (IOException e) {
            logger.error("Failed to read file bytes for upload: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to read file data for upload", e);
        } catch (Exception e) {
            logger.error("Failed to upload file '{}' to S3: {}", key, e.getMessage(), e);
            throw new RuntimeException("Error uploading file to S3 for key: " + key, e);
        }

        // Convert MultipartFile directly to BufferedImage
        BufferedImage image;
        try {
            logger.debug("Converting uploaded file to BufferedImage for OCR...");
            image = ImageIO.read(file.getInputStream());
            if (image == null) {
                throw new IOException("ImageIO.read() returned null — file may be corrupt or unsupported format");
            }
        } catch (IOException e) {
            logger.error("Failed to decode image from uploaded file '{}': {}", file.getOriginalFilename(), e.getMessage(), e);
            throw new RuntimeException("Error decoding uploaded image file", e);
        }

        // Configure and run Tesseract OCR
        try {
            logger.debug("Setting up Tesseract...");
            tesseract.setDatapath(tessDataPath);
            tesseract.setLanguage("eng");
            logger.info("Running OCR on T-chart image...");
            String extractedText = tesseract.doOCR(image);
            logger.info("OCR completed successfully for file: {}", file.getOriginalFilename());
            return extractedText;
        } catch (TesseractException e) {
            logger.error("Tesseract OCR failed for file '{}': {}", file.getOriginalFilename(), e.getMessage(), e);
            throw new RuntimeException("OCR processing failed for file: " + file.getOriginalFilename(), e);
        } catch (Exception e) {
            logger.error("Unexpected OCR error for file '{}': {}", file.getOriginalFilename(), e.getMessage(), e);
            throw new RuntimeException("Unexpected OCR error", e);
        }
    }
}
