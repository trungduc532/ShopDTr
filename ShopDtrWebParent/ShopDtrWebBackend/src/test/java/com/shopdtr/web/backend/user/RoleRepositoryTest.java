package com.shopdtr.web.backend.user;

import com.shopdtr.web.backend.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void repositoryTest() {
        Role roleAdmin = new Role("Admin", "Manager anything");
        Role saveRole = roleRepository.save(roleAdmin);
        assertThat(saveRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRole() {
        Role salesperson = new Role("Salesperson", "Manager product price, customer, " +
                "shipping, orders and sale report");
        Role editor = new Role("Editor", "Manager categories, brands, " +
                "products, articles and menus");
        Role shipper = new Role("Shipper", "View product, view order and update order status");
        Role assistant = new Role("Assistant", "Manager question and reviews");

        roleRepository.saveAll(List.of(salesperson, editor, shipper, assistant));
    }

}