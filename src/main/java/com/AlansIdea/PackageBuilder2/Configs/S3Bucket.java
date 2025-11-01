package com.AlansIdea.PackageBuilder2.Configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "aws.s3.buckets")
@Getter
@Setter
public class S3Bucket {
    private String TchartBucket;
}
