package com.nokia.library.nokiainnovativeproject.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String catalogNumber;

    //////////////////////////////////////////////////////////////
    //////////////////////////////////////////////////////////////
    //private BookDetails bookDetails;
    //////////////////////////////////////////////////////////////////
    /////////////////////////////////////////////////////////////////

    private String comments;

    //////////////////////////////////////////////////////////////
    //private Library library;
    //////////////////////////////////////////////////////////////
}
