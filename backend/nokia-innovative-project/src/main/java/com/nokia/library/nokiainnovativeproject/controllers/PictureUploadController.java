package com.nokia.library.nokiainnovativeproject.controllers;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.nokia.library.nokiainnovativeproject.services.PictureService;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.PICTURES)
public class PictureUploadController {

    private final PictureService pictureService;

    @PostMapping(value = "/upload")
    public ResponseEntity uploadPicture(MultipartFile picture) throws IOException {
        Map uploadResult = pictureService.uploadPicture(picture);
        return  MessageInfo.success(uploadResult.get("secure_url"), Arrays.asList("Picture uploaded."));
    }
}
