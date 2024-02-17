package com.shopdtr.web.backend.repository;

import com.shopdtr.web.backend.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Integer> {

    @Query("Select cat from Category cat where concat(cat.id, ' ', cat.name, ' ', cat.alias) like %?1%")
    public Page<Category> findAll(String keyword, Pageable pageable);

}
