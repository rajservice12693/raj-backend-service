package com.raj.jewellers.utility;

import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.web.multipart.MultipartFile;

import com.raj.jewellers.enums.HttpStatusEnum;
import com.raj.jewellers.exception.CustomException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RajRequestValidator {

    public static void validateImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Image file is required.");
        }

        if (!isSupportedImageType(file.getContentType())) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Invalid image format. Only JPG, PNG, or GIF allowed.");
        }

        if (!hasValidImageExtension(file.getOriginalFilename())) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Invalid image extension.");
        }

        try {
            if (ImageIO.read(file.getInputStream()) == null) {
                throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                        "File is not a valid image.");
            }
        } catch (CustomException e) {
            throw e;
        } catch (IOException e) {
            throw new CustomException(HttpStatusEnum.BAD_REQUEST,
                    "Error reading the image file.");
        }
    }

    private static boolean isSupportedImageType(String contentType) {
        return contentType != null && (contentType.equals("image/jpeg")
                || contentType.equals("image/png")
                || contentType.equals("image/gif"));
    }

    private static boolean hasValidImageExtension(String fileName) {
        return fileName != null && fileName.toLowerCase()
                .matches(".*\\.(jpg|jpeg|png|gif)$");
    }

}
