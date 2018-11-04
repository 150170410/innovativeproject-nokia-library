package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private String comment;
    private Date addDate;
}
