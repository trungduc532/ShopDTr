package com.shopdtr.web.backend.user;

import com.shopdtr.common.Role;
import com.shopdtr.common.User;
import com.shopdtr.web.backend.common.ConstantKey;
import com.shopdtr.web.backend.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public List<User> listUser() {
        return (List<User>) userRepository.findAll(Sort.by("id").ascending());
    }

    public List<Role> listRole() {
        return (List<Role>) roleRepository.findAll();
    }

    public User save(final User user) {
        boolean isUpdatingUser = (user.getId() != null);
        if (isUpdatingUser) {
            final User userExisting = userRepository.findById(user.getId()).get();
            if (user.getPassword().isEmpty()) {
                user.setPassword(userExisting.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    public User updateAccount(User userInForm){
        User userDB = userRepository.getUserByEmail(userInForm.getEmail());

        if(!userInForm.getPassword().isEmpty()){
            userDB.setPassword(userInForm.getPassword());
            encodePassword(userDB);
        }
        if(!userInForm.getPhotos().isEmpty()) {
            userDB.setPhotos(userInForm.getPhotos());
        }
        userDB.setFirstName(userInForm.getFirstName());
        userDB.setLastName(userInForm.getLastName());

        return userRepository.save(userDB);
    }

    public void encodePassword(User user) {
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
    }

    public boolean isEmailUnique(Integer id, String email) {
        User userGetByEmail = userRepository.getUserByEmail(email);

        if (userGetByEmail == null) return true;

        // Check for case create new user
        boolean isCreateNew = (id == null);
        if (isCreateNew) {
            return false;
        } else {
            if (userGetByEmail.getId() != id) return false;
        }
        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try {
            return userRepository.findById(id).get();
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Could not find any user with id " + id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not find any user with id " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnableStatus(final Integer id, final boolean enable) {
        userRepository.updateEnableStatus(id, enable);
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc")? sort.ascending():sort.descending();
        Pageable pageable =  PageRequest.of(pageNum - 1, ConstantKey.USERS_PER_PAGE, sort);
        if (keyword != null) {
            return userRepository.findAll(keyword,pageable);
        }
        return userRepository.findAll(pageable);
    }

    public void testInputStream() throws IOException {
        InputStream inputstream = new FileInputStream("C:\\Users\\Trung Duc\\Desktop\\ducVV.txt");

        String text = "";
        int data = inputstream.read();
        while (data != -1) {
            text = text + String.valueOf((char) data);
            data = inputstream.read();
        }
        inputstream.close();
        System.out.println(text);

    }
}
