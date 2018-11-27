package com.nokia.library.nokiainnovativeproject.controllers;


import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import com.nokia.library.nokiainnovativeproject.entities.Picture;
import com.nokia.library.nokiainnovativeproject.entities.PictureUpload;
import com.nokia.library.nokiainnovativeproject.utils.Mappings;
import com.nokia.library.nokiainnovativeproject.utils.MessageInfo;
import com.nokia.library.nokiainnovativeproject.validators.PictureValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(Mappings.API_VERSION + Mappings.PICTURES)
public class PictureUploadController {

    @PostMapping(value = "/upload")
    public MessageInfo uploadPicture(@ModelAttribute PictureUpload pictureUpload, BindingResult result, ModelMap model) throws IOException {
        PictureValidator validator = new PictureValidator();
        validator.validate(pictureUpload, result);
        Map uploadResult = null;
        if (pictureUpload.getFile() != null && !pictureUpload.getFile().isEmpty()) {
            Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "drkqvtiuz",
                    "api_key", "878127959754956",
                    "api_secret", "tOauaQUIBLKO8Ar7FVOcx0A69d8"));
            uploadResult = cloudinary.uploader().upload(pictureUpload.getFile().getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            Object version = uploadResult.get("version");
        }

        model.addAttribute("picture_url", pictureUpload.getUrl());
        model.addAttribute("picture_url", pictureUpload.getThumbnailUrl());
        MessageInfo errors = MessageInfo.getErrors(result);
        return errors != null ? errors : MessageInfo.success(model,Arrays.asList("Book updated successfully"));
    }
}
