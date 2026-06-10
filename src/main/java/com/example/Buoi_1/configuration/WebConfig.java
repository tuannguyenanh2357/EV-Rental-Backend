package com.example.Buoi_1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Path uploadDir = Paths.get("./uploads");
        String uploadPath = uploadDir.toFile().getAbsolutePath();
        
        // Handle when run from parent workspace root
        if (!new File(uploadPath + "/vf8.png").exists()) {
            File subDir = new File("./Buoi_1/uploads");
            if (subDir.exists()) {
                uploadPath = subDir.getAbsolutePath();
            }
        }
        
        // Ensure directory exists
        File file = new File(uploadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadPath + "/");
    }
}
