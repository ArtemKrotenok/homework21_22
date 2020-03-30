package com.gmail.artemkrotenok.mvc.repository;

import com.gmail.artemkrotenok.mvc.repository.model.User;

public interface UserRepository extends GenericRepository<Long, User> {

    User getUserByUsername(String username);
}
