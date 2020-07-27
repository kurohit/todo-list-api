package com.upgrade.todolist.services;

import com.upgrade.todolist.domain.User;
import com.upgrade.todolist.exceptions.TLResourceNotFoundException;
import com.upgrade.todolist.exceptions.TodoAuthException;
import com.upgrade.todolist.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User validateUser(String email, String password) throws TodoAuthException {
        if(email != null)
            email = email.toLowerCase();

        return userRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public User registerUser(String name, String email, String password) throws TodoAuthException {
        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        if(email != null)
            email = email.toLowerCase();

        if(!pattern.matcher(email).matches())
            throw new TodoAuthException("Invalid email format!!!");

        Integer count = userRepository.getCountByEmail(email);
        if(count > 0)
            throw new TodoAuthException("Email already in use!!!");

        Integer userID = userRepository.addUser(name, email, password);

        return userRepository.findByID(userID);
    }

    @Override
    public void changePassword(String email, String currentPassword, String newPassword) {
        User user = userRepository.findByEmailAndPassword(email, currentPassword);
        if(user != null)
            userRepository.changePassword(email, newPassword);
        else
            throw new TLResourceNotFoundException("Invalid email/current password!!!");
    }
}
