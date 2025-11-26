package com.workoutplanner.MiniProject.Config;

import net.sourceforge.tess4j.Tesseract;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OcrConfig {
    @Value("${tesseract.tessdata-path}")
    private String tessdataPath;

    @Bean
    public Tesseract tesseract() {
        Tesseract t = new Tesseract();
        t.setDatapath(tessdataPath);
        t.setLanguage("eng"); // change if needed
        // optional tuning:
        t.setTessVariable("user_defined_dpi", "300");
        return t;
    }
}
