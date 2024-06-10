package ru.spring_mvc_hibernate.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.spring_mvc_hibernate.model.User;
import ru.spring_mvc_hibernate.service.UserService;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/", "list"})
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "list";
    }

    @GetMapping("/new")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "form";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam(value = "id") Long id, Model model, RedirectAttributes attributes) {
        User user = userService.getUserById(id);

        if (user == null) {
            attributes.addFlashAttribute("message", "User not found");
            return "redirect:/users";
        }
        model.addAttribute("user", user);
        return "form";
    }

    @PostMapping
    public String saveUser(@ModelAttribute("user") User user, RedirectAttributes attributes) {
        userService.saveUser(user);
        attributes.addFlashAttribute("success", "User " +
                user.getLastName() + " " + user.getName() + " save successfully");
        return "redirect:/users";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam(value = "id", required = true, defaultValue = "") Long id, Model model,
                             RedirectAttributes redirectAttributes) {
        User user = userService.deleteUser(id);

        redirectAttributes.addFlashAttribute("user", (null == user) ?
                "User not found" : "User " + user.getLastName() + " " + user.getName() + " deleted");

        return "redirect:/users";
    }
}
