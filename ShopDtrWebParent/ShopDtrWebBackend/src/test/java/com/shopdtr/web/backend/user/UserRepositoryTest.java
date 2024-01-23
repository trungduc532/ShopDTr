package com.shopdtr.web.backend.user;

import com.shopdtr.web.backend.entity.Role;
import com.shopdtr.web.backend.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUser() {

        Role roleAdmin = testEntityManager.find(Role.class, 1);
        User trungDuc = new User("trungduc532@gmail.com", "password", "Duc", "Vu Trung");
        trungDuc.addRole(roleAdmin);
        User saveUser = userRepository.save(trungDuc);
        assertThat(saveUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserTwoRole() {
        User huaVanCuong = new User("huavancuong@gmail.com", "1234567890", "Cuong", "Hua Van");
        Role editerRole = testEntityManager.find(Role.class, 2);
        Role assistantRole = testEntityManager.find(Role.class, 4);
        huaVanCuong.addRole(editerRole);
        huaVanCuong.addRole(assistantRole);
        User saveHuaVanCuong = userRepository.save(huaVanCuong);
        assertThat(saveHuaVanCuong.getId()).isGreaterThan(0);
    }

    @Test
    public void testAllUser() {
        final Iterable<User> listUser = userRepository.findAll();
        listUser.forEach(user -> System.out.println(user.toString()));
    }
}