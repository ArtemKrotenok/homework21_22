package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.service.model.ItemDTO;

import java.util.List;

public interface ItemService {

    void add(ItemDTO itemDTO);

    List<ItemDTO> findAll();

    ItemDTO findById(Long id);

    void deleteById(Long id);
}
