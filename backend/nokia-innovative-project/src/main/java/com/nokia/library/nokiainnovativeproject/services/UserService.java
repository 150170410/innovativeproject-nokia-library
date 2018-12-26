package com.nokia.library.nokiainnovativeproject.services;

import com.nokia.library.nokiainnovativeproject.entities.Address;
import com.nokia.library.nokiainnovativeproject.entities.Role;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AddressRepository;
import com.nokia.library.nokiainnovativeproject.repositories.RoleRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final RoleRepository roleRepository;

    public User createUser(User user) {
        return userRepository.save(persistingRequiredEntities(user));
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findUserByEmail(email);
        if(user != null) {
            Hibernate.initialize(user.getRoles());
            Hibernate.initialize(user.getAddress());
            Hibernate.initialize(user.getReservations());
            Hibernate.initialize(user.getBooks());
            Hibernate.initialize(user.getReviews());
        }
        return user;
    }

    private User persistingRequiredEntities(User user) {
        Address address = user.getAddress();
        if(address != null && address.getId() != null) {
            address = addressRepository.findById(address.getId()).orElseThrow(()-> new ResourceNotFoundException("address"));
            user.setAddress(address);
            return user;
        }
        List<Role> roles = user.getRoles();
        if(roles == null)
            return user;

        List<Role> detachedRoles = new ArrayList<>();
        for(Role role: roles) {
            detachedRoles.add(roleRepository.findByRole(role.getRole()));
        }
        user.setRoles(detachedRoles);
        return user;
    }
}