package com.nokia.library.nokiainnovativeproject.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PictureService {
    public Map uploadPicture(MultipartFile picture) throws IOException {

        validateFile(picture);
        Map uploadResult = null;
        if (picture != null) {
            Cloudinary cloudinary = Singleton.getCloudinary();
            uploadResult = cloudinary.uploader().upload(picture.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
        }
        return uploadResult;
    }

    private void validateFile(MultipartFile file) throws IOException {
        String contentType = file.getContentType();
        if (!contentType.equals(MediaType.IMAGE_JPEG.toString()) && !contentType.equals(MediaType.IMAGE_PNG.toString()))
            throw new IOException("Invalid media type. Make sure file is of type .jpeg or .png.");
    }
}
