package com.nokia.library.nokiainnovativeproject.services;


import com.nokia.library.nokiainnovativeproject.DTOs.UserDTO;
import com.nokia.library.nokiainnovativeproject.entities.Address;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AddressRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public User getUserbyId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user"));
    }

    public User createUser(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);
        return userRepository.save(persistingRequiredEntities(user, userDTO));
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("user"));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        return userRepository.save(user);
    }

    public void deleteUser(Long id)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("user"));
        userRepository.delete(user);
    }

    private User persistingRequiredEntities(User user, UserDTO userDTO) {
        Address address = userDTO.getAddress();
        if(address.getId() != null) {
            address = addressRepository.findById(address.getId()).orElseThrow(()-> new ResourceNotFoundException("address"));
            user.setAddress(address);
            return user;
        }
        return user;
    }
}