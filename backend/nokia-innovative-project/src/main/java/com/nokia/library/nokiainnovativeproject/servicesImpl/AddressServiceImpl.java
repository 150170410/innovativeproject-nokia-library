package com.nokia.library.nokiainnovativeproject.servicesImpl;

import com.nokia.library.nokiainnovativeproject.DTOs.AddressDTO;
import com.nokia.library.nokiainnovativeproject.entities.Address;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AddressRepository;
import com.nokia.library.nokiainnovativeproject.services.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Override
    public List<Address> getAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AddressRepository", "id", id));
    }

    @Override
    public Address createAddress(AddressDTO addressDTO) {
        ModelMapper mapper = new ModelMapper();
        Address address = mapper.map(addressDTO, Address.class);
        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, AddressDTO addressDTO) {
        Address address = addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("AddressRepository", "id", id));
        address.setCity(addressDTO.getCity());
        address.setBuild(addressDTO.getBuild());
        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("AddressRepository", "id", id));
        addressRepository.delete(address);
    }
}
