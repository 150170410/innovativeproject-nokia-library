package com.nokia.library.nokiainnovativeproject.utils;

import com.nokia.library.nokiainnovativeproject.entities.Reservation;

import java.util.Comparator;

public class  ReservationByDateComparator implements Comparator<Reservation>
{
    public  int compare(Reservation x, Reservation y)
    {
        return x.getReservationDate().compareTo(y.getReservationDate());
    }
}