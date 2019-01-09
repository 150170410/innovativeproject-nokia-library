package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    @NotBlank(message = "Comment is required")
    @Size(max = 300, message = "Comment can't exceed  300 characters long")
    private String comment;
}
