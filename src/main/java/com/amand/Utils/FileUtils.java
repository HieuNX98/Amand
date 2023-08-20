package com.amand.Utils;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Component
@Slf4j
public class FileUtils {

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        try {
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));
            return  (String) result.get("secure_url");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
