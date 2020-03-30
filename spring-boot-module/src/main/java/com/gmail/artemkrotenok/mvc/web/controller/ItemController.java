package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.ItemService;
import com.gmail.artemkrotenok.mvc.service.ShopService;
import com.gmail.artemkrotenok.mvc.service.model.ItemDTO;
import com.gmail.artemkrotenok.mvc.service.model.ItemSearchDTO;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;
    private final ShopService shopService;

    public ItemController(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    @GetMapping("page/{numberPage}")
    public String getItemsPage(@PathVariable int numberPage, Model model) {
        Long countItems = itemService.getCountItems();
        model.addAttribute("countItems", countItems);
        model.addAttribute("page", numberPage);
        List<ItemDTO> itemsDTO = itemService.getItemsByPage(numberPage);
        model.addAttribute("items", itemsDTO);
        return "items";
    }

    @GetMapping("/{id}")
    public String getItemPage(@PathVariable Long id, Model model) {
        ItemDTO itemDTO = itemService.findById(id);
        model.addAttribute("item", itemDTO);
        return "item";
    }

    @GetMapping("/delete/{id}")
    public String deleteItem(@PathVariable Long id, Model model) {
        itemService.deleteById(id);
        return "redirect:/items";
    }

    @GetMapping("/add")
    public String addItemPage(Model model) {
        model.addAttribute("item", new ItemDTO());
        List<ShopDTO> shopsDTO = shopService.findAll();
        model.addAttribute("shops", shopsDTO);
        return "item_add";
    }

    @GetMapping("/search")
    public String getItemSearchPage(Model model) {
        model.addAttribute("searchItem", new ItemSearchDTO());
        return "items_search";
    }

    @PostMapping("/search")
    public String getResultSearch(
            @ModelAttribute(name = "itemSearch") ItemSearchDTO itemSearchDTO,
            Model model) {
        Long countItems = itemService.getCountItemsForResultSearch(itemSearchDTO);
        model.addAttribute("countItems", countItems);
        List<ItemDTO> itemsDTO = itemService.searchByParameters(itemSearchDTO);
        model.addAttribute("items", itemsDTO);
        return "items_search_result";
    }

    @PostMapping
    public String addItem(
            Model model,
            @Valid @ModelAttribute(name = "item") ItemDTO itemDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", itemDTO);
            return "item_add";
        }
        itemService.add(itemDTO);
        return "redirect:/items";
    }
}
