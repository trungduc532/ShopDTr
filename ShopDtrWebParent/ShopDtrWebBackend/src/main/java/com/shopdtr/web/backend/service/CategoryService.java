package com.shopdtr.web.backend.service;

import com.shopdtr.web.backend.common.ConstantKey;
import com.shopdtr.web.backend.entity.Category;
import com.shopdtr.web.backend.exception.CategoryNotFoundException;
import com.shopdtr.web.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Category get(Integer id) throws CategoryNotFoundException {
        try {
            return categoryRepo.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new CategoryNotFoundException("Could not find any category with id " + id);
        }
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
}
