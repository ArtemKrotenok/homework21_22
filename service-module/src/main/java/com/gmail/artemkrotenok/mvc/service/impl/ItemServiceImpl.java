package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.ItemRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Item;
import com.gmail.artemkrotenok.mvc.repository.model.ItemDetails;
import com.gmail.artemkrotenok.mvc.repository.model.Shop;
import com.gmail.artemkrotenok.mvc.service.ItemService;
import com.gmail.artemkrotenok.mvc.service.model.ItemDTO;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {
    ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    @Transactional
    public void add(ItemDTO itemDTO) {
        Item item = getObjectFromDTO(itemDTO);
        itemRepository.persist(item);
    }

    @Override
    @Transactional
    public List<ItemDTO> findAll() {
        List<Item> items = itemRepository.findAll();
        return convertItemListToItemListDTO(items);
    }

    @Override
    @Transactional
    public ItemDTO findById(Long id) {
        Item item = itemRepository.findById(id);
        return getDTOFromObject(item);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Item item = itemRepository.findById(id);
        itemRepository.remove(item);
    }

    private List<ItemDTO> convertItemListToItemListDTO(List<Item> itemList) {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            itemDTOList.add(getDTOFromObject(item));
        }
        return itemDTOList;
    }

    private ItemDTO getDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        if (item.getItemDetails() != null) {
            String priceFromDatabase = item.getItemDetails().getPrice();
            BigDecimal price = BigDecimal.valueOf(Double.parseDouble(priceFromDatabase));
            itemDTO.setPrice(price);
        }
        itemDTO.setShops(convertShopListToShopDTOList(item.getShops()));
        return itemDTO;
    }

    private Item getObjectFromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setItem(item);
        itemDetails.setPrice(itemDTO.getPrice().toString());
        item.setItemDetails(itemDetails);
        item.setShops(convertShopDTOListToShopList(itemDTO.getShops()));
        return item;
    }

    private List<Shop> convertShopDTOListToShopList(List<ShopDTO> shopDTOList) {
        List<Shop> shopList = new ArrayList<>();
        for (ShopDTO shopDTO : shopDTOList) {
            shopList.add(ShopServiceImpl.getObjectFromDTO(shopDTO));
        }
        return shopList;
    }

    private List<ShopDTO> convertShopListToShopDTOList(List<Shop> shopList) {
        List<ShopDTO> shopDTOList = new ArrayList<>();
        for (Shop shop : shopList) {
            shopDTOList.add(ShopServiceImpl.getDTOFromObject(shop));
        }
        return shopDTOList;
    }
}
