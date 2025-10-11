package com.raj.jewellers.utility;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.raj.jewellers.constants.AppProperties;
import com.raj.jewellers.enums.HttpStatusEnum;
import com.raj.jewellers.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class Utility {

    @Autowired
    private AppProperties appProperties;

    public String uploadAndGenerateImagePath(MultipartFile file) {
        try {
            RajRequestValidator.validateImage(file);
            String fileName = file.getOriginalFilename();
            StringBuilder sb = new StringBuilder();
            sb.append(appProperties.getDocPathPrefix())
                    .append(File.separator);

            if (!Files.exists(Paths.get(sb.toString()))) {
                Files.createDirectories(Paths.get(sb.toString()));
            }

            SimpleDateFormat dateFormat =
                    new SimpleDateFormat("yyyyMM_HHmmssSSS");
            String currenttimestamp = dateFormat.format(new Date());

            String fileNameWithTime = fileName.replaceAll("\\.(\\w+)$",
                    "_" + currenttimestamp + ".$1");
            sb.append(fileNameWithTime);

            String completeImagePath = sb.toString();

            Files.copy(file.getInputStream(), Paths.get(completeImagePath),
                    StandardCopyOption.REPLACE_EXISTING);

            return completeImagePath;
        } catch (CustomException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error creating image path: {} : {}",
                    e.getStackTrace()[0], e.getMessage());
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Unable to create impage path.");
        }
    }

    public static String imageFileToBase64(String imagePath) {
        try {
            byte[] imageBytes = Files.readAllBytes(Path.of(imagePath));
            return Base64.getEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            log.error("Error while converting base64: {}", e.getMessage());
        }
        return null;

    }

    public static String toTitleCase(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        String[] words = input.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)));
                sb.append(word.substring(1).toLowerCase());
                sb.append(" ");
            }
        }

        return sb.toString().trim();
    }
}
