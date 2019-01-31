package com.nokia.library.nokiainnovativeproject.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookToOrderDTO {

    private Long id;

    @Size(min = 10, max = 17, message = "ISBN must be 10-17 numbers long")
    @NotBlank(message = "ISBN is required")
    @Pattern(regexp = "(([0-9Xx][- ]*){13}|([0-9Xx][- ]*){10})",
            message = "ISBN is not valid")
    private String isbn;

    @Length(max = 100, message = "Title can't exceed 100 characters")
    @NotBlank(message = "Title is required")
    private String title;

    private boolean isSubscribed;

    private boolean isRemovable;

    private Integer totalSubs;
}
