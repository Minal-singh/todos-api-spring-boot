package com.minal.todo.services;

import com.minal.todo.models.UserModel;
import com.minal.todo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Iterable<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<UserModel> getUserByUserName(String userName){
        return userRepository.findByUserName(userName);
    }

    public void createUser(UserModel newUser){
        userRepository.save(newUser);
    }

    public Optional<UserModel> updateUser(String userName, UserModel newUser){
        UserModel oldUser = userRepository.findByUserName(userName).orElse(null);
        if (oldUser != null){
            if (newUser.getUserName() != null && !newUser.getUserName().trim().isEmpty()) {
                oldUser.setUserName(newUser.getUserName().trim());
            }
            if (newUser.getPassword() != null && !newUser.getPassword().trim().isEmpty()) {
                oldUser.setPassword(newUser.getPassword().trim());
            }
            userRepository.save(oldUser);
            return Optional.of(oldUser);
        }
        return Optional.empty();
    }

    public void deleteUser(String userName){
        userRepository.deleteByUserName(userName);
    }
}
