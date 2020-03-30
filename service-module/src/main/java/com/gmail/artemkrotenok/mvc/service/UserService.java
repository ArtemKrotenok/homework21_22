package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.service.model.AppUserPrincipal;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;

public interface UserService {

    void add(UserDTO userDTO);

    UserDTO getUserByUsername(String username);

    AppUserPrincipal getCurrentUser();

}
