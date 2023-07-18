package com.shopdtr.web.backend.user;

import com.shopdtr.common.Role;
import com.shopdtr.common.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> listUser() {
        return (List<User>) userRepository.findAll();
    }

    public List<Role> listRole() {
        return (List<Role>) roleRepository.findAll();
    }

    public void save(final User user) {
        encodePassword(user);
        userRepository.save(user);
    }

    public void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean isEmailUnique(String email) {
        User userGetByEmail = userRepository.getUserByEmail(email);
        return userGetByEmail == null;
    }
}
