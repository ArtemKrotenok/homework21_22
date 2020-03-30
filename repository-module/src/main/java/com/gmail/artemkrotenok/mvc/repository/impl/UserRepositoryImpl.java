package com.gmail.artemkrotenok.mvc.repository.impl;

import com.gmail.artemkrotenok.mvc.repository.UserRepository;
import com.gmail.artemkrotenok.mvc.repository.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;

@Repository
public class UserRepositoryImpl extends GenericRepositoryImpl<Long, User> implements UserRepository {

    @Override
    public User getUserByUsername(String username) {
        String hql = "FROM " + entityClass.getSimpleName() + " u WHERE u.username=:username";
        Query query = entityManager.createQuery(hql);
        query.setParameter("username", username);
        return (User) query.getSingleResult();
    }
}
