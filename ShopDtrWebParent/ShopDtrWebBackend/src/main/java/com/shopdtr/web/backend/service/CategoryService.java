package com.shopdtr.web.backend.service;

import com.shopdtr.web.backend.common.ConstantKey;
import com.shopdtr.web.backend.entity.Category;
import com.shopdtr.web.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryService {

    @Autowired()
    private CategoryRepository categoryRepo;

    public List<Category> listCategory() {
        return (List<Category>) categoryRepo.findAll(Sort.by("id").ascending());
    }

    public Category save(final Category category) {
        return categoryRepo.save(category);
    }

    /**
     * Get a list category to fill for parent dropdown in category form
     * @return
     */
    public List<Category> getListCategory() {
        List<Category> listCategory = new ArrayList<>();
        Iterable<Category> categoriesDB = categoryRepo.findAll();

        for (Category category : categoriesDB) {
            if(category.getParent() == null) {
                listCategory.add(new Category(category.getName()));

                Set<Category> children = category.getChildren();

                for (Category subCategory: children) {
                    String name = "--" + subCategory.getName();
                    listCategory.add(new Category(name));

                    getListChildren(listCategory, subCategory, 1);
                }
            }
        }

        return listCategory;
    }

    public Page<Category> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc")? sort.ascending():sort.descending();
        Pageable pageable =  PageRequest.of(pageNum - 1, ConstantKey.CATEGORY_NUM_PAGE, sort);
        if (keyword != null) {
            return categoryRepo.findAll(keyword,pageable);
        }
        return categoryRepo.findAll(pageable);
    }

    private void getListChildren(List<Category> listCategory, Category parent, int subLevel) {
        int newLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();

        for (Category subCategory : children) {
            String name = "";
            for (int i = 0; i < newLevel; i++) {
                name += "--";
            }
            name += subCategory.getName();
            listCategory.add(new Category(name));
            getListChildren(listCategory, subCategory, newLevel);
        }
    }
}
