package com.nokia.library.nokiainnovativeproject.services;


import com.nokia.library.nokiainnovativeproject.DTOs.UserDTO;
import com.nokia.library.nokiainnovativeproject.entities.Address;
import com.nokia.library.nokiainnovativeproject.entities.Role;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.AddressRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        User user = userRepository.findUserByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Sorry we can't find user with email: " + email);
        }
        Hibernate.initialize(user.getRoles());
        return buildUserForAuthentication(user, buildUserAuthority(user.getRoles()));
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role: roles){
            authorities.add( new SimpleGrantedAuthority(role.getRole()));
        }
        return new ArrayList<>(authorities);
    }

    public User getLoggedInUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principal instanceof org.springframework.security.core.userdetails.User) {
            org.springframework.security.core.userdetails.User loggedInUser =
                    (org.springframework.security.core.userdetails.User) principal;
            return userRepository.findUserByEmail(loggedInUser.getUsername());
        }
        return null;
    }

    public User getUserById(Long id) {
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