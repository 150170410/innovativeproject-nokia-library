package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailsDTO {

    private Integer isbn;
    private String title;
    private String description;
    private String coverPictureUrl;
    private Date dateOfPublication;
    private String tableOfContents;
}
