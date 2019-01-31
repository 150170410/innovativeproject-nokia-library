package com.nokia.library.nokiainnovativeproject.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalWithActualOwner extends Rental implements Serializable {

    private Long id;

    private User actualOwner;

    private List<User> owners;

    private Date rentalDate;
}
