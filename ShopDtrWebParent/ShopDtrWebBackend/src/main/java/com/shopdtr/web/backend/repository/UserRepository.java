package com.shopdtr.web.backend.repository;

import com.shopdtr.web.backend.entity.User;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Integer> {

    @Query("Select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("Select u from User u where concat(u.id, ' ', u.firstName, ' ', u.lastName, ' ', u.email) like %?1%")
    public Page<User> findAll(String keyword, Pageable pageable);

    @Query("update User u set u.enable = ?2 where u.id = ?1")
    @Modifying
    public void updateEnableStatus(Integer id, boolean enable);

}
