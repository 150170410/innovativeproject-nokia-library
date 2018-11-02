package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.AddressDTO;
import com.nokia.library.nokiainnovativeproject.entities.Address;

import java.util.List;

public interface AddressService {

    List<Address> getAllAddress();

    Address getAddressById(Long id);

    Address createAddress(AddressDTO addressDTO);

    Address updateAddress(Long id, AddressDTO addressDTO);

    void deleteAddress(Long id);
}
