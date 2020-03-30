package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.UserRepository;
import com.gmail.artemkrotenok.mvc.repository.model.User;
import com.gmail.artemkrotenok.mvc.service.UserService;
import com.gmail.artemkrotenok.mvc.service.model.AppUserPrincipal;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void add(UserDTO userDTO) {
        User user = getObjectFromDTO(userDTO);
        userRepository.persist(user);
    }

    @Override
    @Transactional
    public UserDTO getUserByUsername(String username) {
        User user = userRepository.getUserByUsername(username);
        UserDTO userDTO = null;
        if (user != null) {
            userDTO = getDTOFromObject(user);
        }
        return userDTO;
    }

    @Override
    public AppUserPrincipal getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (AppUserPrincipal) authentication.getPrincipal();
        } else {
            return null;
        }
    }

    private UserDTO getDTOFromObject(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(user.getUsername());
        userDTO.setPassword(user.getPassword());
        userDTO.setRole(user.getRole());
        return userDTO;
    }

    private User getObjectFromDTO(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt()));
        user.setRole(userDTO.getRole());
        return user;
    }
}
