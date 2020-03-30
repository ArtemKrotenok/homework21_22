package com.gmail.artemkrotenok.mvc.service;

import com.gmail.artemkrotenok.mvc.service.model.ItemDTO;
import com.gmail.artemkrotenok.mvc.service.model.ItemSearchDTO;

import java.util.List;

public interface ItemService {

    void add(ItemDTO itemDTO);

    List<ItemDTO> findAll();

    ItemDTO findById(Long id);

    void deleteById(Long id);

    List<ItemDTO> getItemsByPage(int page);

    List<ItemDTO> searchByParameters(ItemSearchDTO itemSearchDTO);

    Long getCountItems();

    Long getCountItemsForResultSearch(ItemSearchDTO itemSearchDTO);
}
