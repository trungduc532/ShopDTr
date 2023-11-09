package com.shopdtr.web.backend.security;

import com.shopdtr.common.User;
import com.shopdtr.web.backend.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ShopDtrUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.getUserByEmail(email);
        if (user != null) {
            return new ShopDtrUserDetails(user);
        }
        throw new UsernameNotFoundException("Can not find the user with email: " + email);
    }
}
