package com.shopdtr.web.backend.user;

import com.shopdtr.common.Role;
import com.shopdtr.common.User;
import com.shopdtr.web.backend.FileUploadUtils;
import com.shopdtr.web.backend.common.ConstantKey;
import com.shopdtr.web.backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public String listFirstPage(final Model model) {
        return listByPage(1, model, "id", "asc", null);
    }

    @GetMapping("/users/new_user")
    public String createNewUser(Model model) {
        final List<Role> roleList = userService.listRole();
        final User user = new User();
        user.setEnable(true);
        model.addAttribute("user", user);
        model.addAttribute("roleList", roleList);
        model.addAttribute("pageTitle", "Create new user");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(final User user, RedirectAttributes redirectAttributes,
                           @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if (!multipartFile.isEmpty()) {
            String uploadDir = "user_photos/" + user.getId();
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            userService.save(user);
            FileUploadUtils.clearDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            userService.save(user);
        }
        redirectAttributes.addFlashAttribute("message", "The user have been saved successfully.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Integer id,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        try {
            User user = userService.get(id);
            final List<Role> roleList = userService.listRole();

            // Binding data with model to return client
            model.addAttribute("user", user);
            model.addAttribute("pageTitle", "Update User (ID : " + id + ")");
            model.addAttribute("roleList", roleList);
            return "user_form";
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/users";
        }
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id,
                             RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "A user has been deleted successfully");
        } catch (UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/users";
    }

    @GetMapping("users/{id}/enabled/{status}")
    public String updateUserEnableStatus(@PathVariable("id") Integer id,
                                         @PathVariable("status") boolean enabled,
                                         RedirectAttributes redirectAttributes) {
        userService.updateUserEnableStatus(id, enabled);
        String status = enabled ? "enabled" : "disabled";
        String message = "The user Id " + id + " has been" + status;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
    @GetMapping("users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        Page<User> page = userService.listByPage(pageNum, sortField, sortDir, keyword);
        List<User> listUsers = page.getContent();

        // Show message footer page
        long startCount = ((pageNum - 1) * ConstantKey.USERS_PER_PAGE + 1);
        long endCount = startCount - 1 + ConstantKey.USERS_PER_PAGE;
        if(endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        // Create variable revertSortDir
        String revertSortDir = sortDir.equals("asc") ? "desc" : "asc";
        // Display list user to screen.
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("listUsers", listUsers);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("revertSortDir", revertSortDir);
        model.addAttribute("keyword", keyword);
        return "users";
    }
}
