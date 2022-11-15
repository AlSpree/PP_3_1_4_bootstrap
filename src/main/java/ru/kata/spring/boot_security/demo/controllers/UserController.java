package ru.kata.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.UserServiceImpl;




@Controller
public class UserController {


    private final UserServiceImpl userServiceImpl;

    @Autowired
    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping("/admin")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("admin", userServiceImpl.findUserByName(authentication.getName()));
        model.addAttribute("users", userServiceImpl.findAll());
        model.addAttribute("newUser", new User());
        model.addAttribute("newRole", new Role());
        model.addAttribute("currentUser", userServiceImpl.findUserByName(authentication.getName()));
        model.addAttribute("allRoles", userServiceImpl.getRoleList());

        return "show-all_bootstrap";
    }

    @GetMapping("/user")
    public String indexUser(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("foundIdUser", userServiceImpl.getUser(authentication.getName()));
        return "/user-info_bootstrap";
    }

    @PostMapping("/admin")
    public String create(@ModelAttribute("newUser") User user,
                         @ModelAttribute("newRole") Role role) {

        userServiceImpl.saveNewUser(user, role.getRole());
        return "redirect:/admin";
    }


    @PatchMapping("/admin/{id}")
    public String update(@ModelAttribute("roleUpdate") Role role,
                         @ModelAttribute("userUpdate") User user, @PathVariable("id") int id) {

        userServiceImpl.updateUser(user, role.getRole());
        return "redirect:/admin";
    }


    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") int id) {
        userServiceImpl.delete(id);
        return "redirect:/admin";
    }
}
