package com.gmail.artemkrotenok.mvc.repository;

import com.gmail.artemkrotenok.mvc.repository.model.Item;

import java.math.BigDecimal;
import java.util.List;

public interface ItemRepository extends GenericRepository<Long, Item> {
    List<Item> searchByParameters
            (String name, String description, BigDecimal minPrice, BigDecimal maxPrice);

    Long getCountItems();

    Long getCountItemsForResultSearch(String name, String description, BigDecimal minPrice, BigDecimal maxPrice);
}
