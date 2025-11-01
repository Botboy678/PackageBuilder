package com.AlansIdea.PackageBuilder2.Configs;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfigs {

    @Bean
    public Tesseract tesseract(){
        return new Tesseract();
    }

}
