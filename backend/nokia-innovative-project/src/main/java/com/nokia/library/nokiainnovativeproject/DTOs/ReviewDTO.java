package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    @NotNull(message = "Comment is required")
    @Size(max = 300, message = "Comment can exceed 300 characters")
    private String comment;

    @NotNull(message = "Comment date is required")
    @Past(message = "Comment date must be present")
    private Date addDate;
}
