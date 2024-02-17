package com.shopdtr.web.backend.repository;

import com.shopdtr.web.backend.service.UserService;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UserServiceTest {

    @Test
    public void testInputStreamFunction() throws IOException {
        UserService userService = new UserService();
        userService.testInputStream();
    }
}