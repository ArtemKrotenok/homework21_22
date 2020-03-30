package com.gmail.artemkrotenok.mvc.service.impl;

import com.gmail.artemkrotenok.mvc.repository.ItemRepository;
import com.gmail.artemkrotenok.mvc.repository.ShopRepository;
import com.gmail.artemkrotenok.mvc.repository.model.Item;
import com.gmail.artemkrotenok.mvc.repository.model.ItemDetails;
import com.gmail.artemkrotenok.mvc.repository.model.Shop;
import com.gmail.artemkrotenok.mvc.service.ItemService;
import com.gmail.artemkrotenok.mvc.service.model.ItemDTO;
import com.gmail.artemkrotenok.mvc.service.model.ItemSearchDTO;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import com.gmail.artemkrotenok.mvc.service.util.ShopConverterUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.gmail.artemkrotenok.mvc.service.constants.PageConstants.ITEMS_BY_PAGE;

@Service
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;
    private final ShopRepository shopRepository;

    public ItemServiceImpl(ItemRepository itemRepository, ShopRepository shopRepository) {
        this.itemRepository = itemRepository;
        this.shopRepository = shopRepository;
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

    @Override
    @Transactional
    public List<ItemDTO> getItemsByPage(int page) {
        int startPosition = ((page - 1) * ITEMS_BY_PAGE + 1) - 1;
        List<Item> items = itemRepository.getItemsByPage(startPosition, ITEMS_BY_PAGE);
        return convertItemListToItemListDTO(items);
    }

    @Override
    @Transactional
    public List<ItemDTO> searchByParameters(ItemSearchDTO itemSearchDTO) {
        itemSearchDTO = formatetSearchDTO(itemSearchDTO);
        List<Item> items = itemRepository.searchByParameters(
                itemSearchDTO.getName(),
                itemSearchDTO.getDescription(),
                itemSearchDTO.getMinPrice(),
                itemSearchDTO.getMaxPrice());
        return convertItemListToItemListDTO(items);
    }

    @Override
    @Transactional
    public Long getCountItems() {
        return itemRepository.getCountItems();
    }

    @Override
    @Transactional
    public Long getCountItemsForResultSearch(ItemSearchDTO itemSearchDTO) {
        itemSearchDTO = formatetSearchDTO(itemSearchDTO);
        return itemRepository.getCountItemsForResultSearch(
                itemSearchDTO.getName(),
                itemSearchDTO.getDescription(),
                itemSearchDTO.getMinPrice(),
                itemSearchDTO.getMaxPrice());
    }

    private ItemSearchDTO formatetSearchDTO(ItemSearchDTO itemSearchDTO) {
        if (itemSearchDTO.getMinPrice() == null) {
            itemSearchDTO.setMinPrice(BigDecimal.ZERO);
        }
        if (itemSearchDTO.getMaxPrice() == null) {
            itemSearchDTO.setMaxPrice(BigDecimal.valueOf(Double.MAX_VALUE));
        }
        return itemSearchDTO;
    }

    private List<ItemDTO> convertItemListToItemListDTO(List<Item> itemList) {
        List<ItemDTO> itemDTOList = new ArrayList<>();
        for (Item item : itemList) {
            ItemDTO dtoFromObject = getDTOFromObject(item);
            itemDTOList.add(dtoFromObject);
        }
        return itemDTOList;
    }

    private ItemDTO getDTOFromObject(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setDescription(item.getDescription());
        if (item.getItemDetails() != null) {
            itemDTO.setPrice(item.getItemDetails().getPrice());
        }
        itemDTO.setShops(convertShopListToShopDTOList(item.getShops()));
        return itemDTO;
    }

    private Item getObjectFromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setName(itemDTO.getName());
        item.setDescription(itemDTO.getDescription());
        ItemDetails itemDetails = new ItemDetails();
        itemDetails.setItem(item);
        itemDetails.setPrice(itemDTO.getPrice());
        item.setItemDetails(itemDetails);
        List<Shop> shops = new ArrayList<>();
        if (itemDTO.getShopIds() != null) {
            for (Long shopId : itemDTO.getShopIds()) {
                shops.add(shopRepository.findById(shopId));
            }
        }
        item.setShops(shops);
        return item;
    }

    private List<ShopDTO> convertShopListToShopDTOList(List<Shop> shopList) {
        List<ShopDTO> shopDTOList = new ArrayList<>();
        for (Shop shop : shopList) {
            ShopDTO dtoFromObject = ShopConverterUtil.getDTOFromObject(shop);
            shopDTOList.add(dtoFromObject);
        }
        return shopDTOList;
    }
}
