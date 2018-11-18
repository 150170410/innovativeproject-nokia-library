package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {

    @NotNull(message = "Comment is required")
    @Size(min = 1, max = 300, message = "Comment must be 1-300 characters long")
    private String comment;
}
