package com.shopdtr.web.backend.user;

import com.shopdtr.common.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    @Query("Select u from User u where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    public Long countById(Integer id);

    @Query("update User u set u.enable = ?2 where u.id = ?1")
    @Modifying
    public void updateEnableStatus(Integer id, boolean enable);

}
