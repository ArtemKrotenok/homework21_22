package com.gmail.artemkrotenok.mvc.web.controller;

import com.gmail.artemkrotenok.mvc.service.UserService;
import com.gmail.artemkrotenok.mvc.service.model.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/add")
    public String addUserPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "user_add";
    }

    @PostMapping()
    public String addUser(
            Model model,
            @Valid @ModelAttribute(name = "user") UserDTO userDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "user_add";
        }
        userService.add(userDTO);
        return "redirect:/home";
    }
}
