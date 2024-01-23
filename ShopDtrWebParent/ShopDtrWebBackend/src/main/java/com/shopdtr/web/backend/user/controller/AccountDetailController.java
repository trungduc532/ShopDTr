package com.shopdtr.web.backend.user.controller;

import com.shopdtr.web.backend.entity.User;
import com.shopdtr.web.backend.FileUploadUtils;
import com.shopdtr.web.backend.security.ShopDtrUserDetails;
import com.shopdtr.web.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class AccountDetailController {

    @Autowired
    private UserService userService;

    @GetMapping("/account")
    public String viewUserDetail(@AuthenticationPrincipal ShopDtrUserDetails userDetails, Model model) {
        // Get email of user login
        final String email = userDetails.getUsername();
        final User user = userService.getUserByEmail(email);
        model.addAttribute("user", user);
        return "users/account_form";
    }

    @PostMapping("/account/update")
    public String updateUserDetails(final User userInForm, final RedirectAttributes redirectAttributes,
                                  @AuthenticationPrincipal ShopDtrUserDetails loginUsser,
                                  @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()) {
            String uploadDir = "user_photos/" + userInForm.getId();
            String fileName = multipartFile.getOriginalFilename();
            System.out.println(fileName);
            fileName = StringUtils.cleanPath(fileName);
            userInForm.setPhotos(fileName);
            userService.updateAccount(userInForm);
            FileUploadUtils.clearDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (userInForm.getPhotos().isEmpty()) userInForm.setPhotos(null);
            userService.updateAccount(userInForm);
        }
        // Update account login to display on the tab bar
        loginUsser.setFirstName(userInForm.getFirstName());
        loginUsser.setLastName(userInForm.getLastName());

        redirectAttributes.addFlashAttribute("message", "Your account details have been updated.");
        return "redirect:/account";
    }

}
