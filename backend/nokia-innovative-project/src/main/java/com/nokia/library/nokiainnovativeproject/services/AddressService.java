package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.DTOs.AddressDTO;
import com.nokia.library.nokiainnovativeproject.entities.Address;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;

    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AddressRepository", "id", id));
    }

    public Address createAddress(AddressDTO addressDTO) {
        ModelMapper mapper = new ModelMapper();
        Address address = mapper.map(addressDTO, Address.class);
        return addressRepository.save(address);
    }

    public Address updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AddressRepository", "id", id));
        address.setCity(addressDTO.getCity());
        address.setBuilding(addressDTO.getBuilding());
        return addressRepository.save(address);
    }

    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AddressRepository", "id", id));
        addressRepository.delete(address);
    }
}
