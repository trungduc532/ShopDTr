package com.shopdtr.web.backend.controller;

import com.shopdtr.web.backend.common.ConstantKey;
import com.shopdtr.web.backend.entity.Category;
import com.shopdtr.web.backend.exporter.CategoryCSVExporter;
import com.shopdtr.web.backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/categories")
    public String listFirstPage(final Model model) {
        return listByPage(1, model, "id", "asc", null);
    }


    @GetMapping("/categories/exports/csv")
    public void exportToCsv(HttpServletResponse response) throws IOException {
        List<Category> listCategory = categoryService.listCategory();
        CategoryCSVExporter categoryCSVExporter = new CategoryCSVExporter();
        categoryCSVExporter.export(listCategory, response);
    }

    @GetMapping("/categories/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum,
                             Model model,
                             @Param("sortField") String sortField,
                             @Param("sortDir") String sortDir,
                             @Param("keyword") String keyword) {
        Page<Category> page = categoryService.listByPage(pageNum, sortField, sortDir, keyword);
        List<Category> listCategory = page.getContent();

        // Show message footer page
        long startCount = ((pageNum - 1) * ConstantKey.CATEGORY_NUM_PAGE + 1);
        long endCount = startCount - 1 + ConstantKey.CATEGORY_NUM_PAGE;
        if(endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }

        // Create variable revertSortDir
        String revertSortDir = sortDir.equals("asc") ? "desc" : "asc";
        // Display list categories to screen.
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("listCategory", listCategory);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("revertSortDir", revertSortDir);
        model.addAttribute("keyword", keyword);
        return "categories/categories";
    }
}
