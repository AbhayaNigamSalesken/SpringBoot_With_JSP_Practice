package com.example.userdetails.controller;

import com.example.userdetails.service.User;
import com.example.userdetails.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/viewUser")
    public String viewUser(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "view-users";
    }

    @GetMapping("/addUser")
    public String addUserView(Model model) {
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/addBook")
    public RedirectView addUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        final RedirectView redirectView = new RedirectView("/user/addUser", true);
        User savedUser = userService.addUser(user);
        redirectAttributes.addFlashAttribute("savedUser", savedUser);
        redirectAttributes.addFlashAttribute("addUserSuccess", true);
        return redirectView;
    }
}
