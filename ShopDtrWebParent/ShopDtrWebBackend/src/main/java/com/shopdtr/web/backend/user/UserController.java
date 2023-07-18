package com.shopdtr.web.backend.user;

import com.shopdtr.common.Role;
import com.shopdtr.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listUserPage(Model model) {
        List<User> listUsers = userService.listUser();
        model.addAttribute("listUsers", listUsers);
        return "users";
    }

    @GetMapping("/users/new_user")
    public String createNewUser(Model model) {
        final List<Role> roleList = userService.listRole();
        final User user = new User();
        user.setEnable(true);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(final User user, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("message", "The user have been saved successfully.");
        userService.save(user);
        return "redirect:/users";
    }


}
