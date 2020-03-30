package com.gmail.artemkrotenok.mvc.repository.impl;

import com.gmail.artemkrotenok.mvc.repository.ItemRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;

@Repository
public class ItemRepositoryImpl extends GenericRepositoryImpl<Long, Item> implements ItemRepository {

    @Override
    public List<Item> searchByParameters(
            String name, String description, BigDecimal minPrice, BigDecimal maxPrice
    ) {
        String hql = "FROM " + entityClass.getSimpleName() + " i" +
                " WHERE i.itemDetails.price>=:minPrice " +
                " AND i.itemDetails.price<=:maxPrice" +
                " AND i.name LIKE CONCAT('%', :name, '%')" +
                " AND i.description LIKE CONCAT('%', :description, '%')";
        Query query = entityManager.createQuery(hql);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        query.setParameter("name", name);
        query.setParameter("description", description);
        return (List<Item>) query.getResultList();
    }

    @Override
    public Long getCountItemsForResultSearch(
            String name, String description, BigDecimal minPrice, BigDecimal maxPrice) {
        String hql = "SELECT COUNT(*) FROM " + entityClass.getSimpleName() + " i" +
                " WHERE i.itemDetails.price>=:minPrice " +
                " AND i.itemDetails.price<=:maxPrice" +
                " AND i.name LIKE CONCAT('%', :name, '%')" +
                " AND i.description LIKE CONCAT('%', :description, '%')";
        Query query = entityManager.createQuery(hql);
        query.setParameter("minPrice", minPrice);
        query.setParameter("maxPrice", maxPrice);
        query.setParameter("name", name);
        query.setParameter("description", description);
        return (Long) query.getSingleResult();
    }

    @Override
    public Long getCountItems() {
        String hql = "SELECT COUNT(*) FROM " + entityClass.getSimpleName();
        Query query = entityManager.createQuery(hql);
        return (Long) query.getSingleResult();
    }
}
