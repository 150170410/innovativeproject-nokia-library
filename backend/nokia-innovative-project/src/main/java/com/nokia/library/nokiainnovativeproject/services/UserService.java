package com.nokia.library.nokiainnovativeproject.services;


import com.nokia.library.nokiainnovativeproject.DTOs.UserDTO;
import com.nokia.library.nokiainnovativeproject.entities.Role;
import com.nokia.library.nokiainnovativeproject.entities.User;
import com.nokia.library.nokiainnovativeproject.exceptions.ResourceNotFoundException;
import com.nokia.library.nokiainnovativeproject.repositories.RoleRepository;
import com.nokia.library.nokiainnovativeproject.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserbyId(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User createUser(UserDTO userDTO) {
        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);

        // everyone user should have employee role
        List<Role> roles = roleRepository.findRoleByName("ROLE_EMPLOYEE");
        Role role;
        if(roles == null || roles.size() == 0){
            role = new Role();
            role.setRole("ROLE_EMPLOYEE");
            roleRepository.save(role);
        }else {
            role = roles.get(0);
        }
        roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);


        Logger logger = Logger.getLogger(getClass().getName());
        logger.info("info");

        return userRepository.save(user);
    }

    public User updateUser(Long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setAddress(userDTO.getAddress());
        user.setPassword(passwordEncoder().encode(userDTO.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(Long id)
            throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<User> users = userRepository.findUsersByEmail(username);
        User user = users != null ? users.get(0) : null;
        return buildUserForAuthentication(user, buildUserAuthority(user.getRoles()));
    }

    private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(user.getEmail(), passwordEncoder().encode(user.getPassword()), authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(List<Role> roles) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for(Role role: roles){
            authorities.add( new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> result = new ArrayList<>(authorities);
        return result;
    }
}