package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.ItemService;
import com.gmail.artemkrotenok.mvc.service.ShopService;
import com.gmail.artemkrotenok.mvc.service.model.ItemDTO;
import com.gmail.artemkrotenok.mvc.service.model.ShopDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("items")
public class ItemController {
    private final ItemService itemService;
    private final ShopService shopService;

    public ItemController(ItemService itemService, ShopService shopService) {
        this.itemService = itemService;
        this.shopService = shopService;
    }

    @GetMapping()
    public String getItems(Model model) {
        List<ItemDTO> itemsDTO = itemService.findAll();
        model.addAttribute("items", itemsDTO);
        return "items";
    }

    @GetMapping("/{id}")
    public String getItem(@PathVariable Long id, Model model) {
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

    @PostMapping
    public String addItem(
            @Valid @ModelAttribute(name = "item") ItemDTO itemDTO,
            @RequestParam(name = "selectedShopsIdList") String[] selectedShopsIdList,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("item", itemDTO);
            return "item_add";
        }
        for (String selectedShopsId : selectedShopsIdList) {
            ShopDTO shopDTO = shopService.findById(Long.parseLong(selectedShopsId));
            itemDTO.getShops().add(shopDTO);
        }
        itemService.add(itemDTO);
        return "redirect:/items";
    }
}
