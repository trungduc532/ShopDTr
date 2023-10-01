package com.shopdtr.web.backend.user;

import org.junit.jupiter.api.Test;

import java.io.IOException;

public class UserServiceTest {

    @Test
    public void testInputStreamFunction() throws IOException {
        UserService userService = new UserService();
        userService.testInputStream();
    }
}