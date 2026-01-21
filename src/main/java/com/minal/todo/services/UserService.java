package com.minal.todo.services;

import com.minal.todo.dto.UserRequestDTO;
import com.minal.todo.dto.UserResponseDTO;
import com.minal.todo.dto.UserUpdateDTO;
import com.minal.todo.exceptions.UserAlreadyExistsException;
import com.minal.todo.exceptions.UserNotFoundException;
import com.minal.todo.mapper.UserMapper;
import com.minal.todo.models.UserModel;
import com.minal.todo.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public UserService(UserMapper userMapper, UserRepository userRepository){
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public List<UserResponseDTO> getAllUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toDto).collect(Collectors.toList());
    }

    public UserResponseDTO getUserByUserName(String userName){
        UserModel user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        return userMapper.toDto(user);
    }

    public UserResponseDTO createUser(UserRequestDTO userDto){
        if (userRepository.findByUserName(userDto.getUserName()).isPresent()){
            throw new UserAlreadyExistsException("User exists");
        }
        UserModel user = userMapper.toEntity(userDto);
        UserModel saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    public UserResponseDTO updateUser(String userName, UserUpdateDTO userDto){
        UserModel oldUser = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (userDto.getUserName()!=null && !userDto.getUserName().trim().isEmpty()) {
            oldUser.setUserName(userDto.getUserName().trim());
        }
        if (userDto.getPassword()!=null && !userDto.getPassword().trim().isEmpty()) {
            oldUser.setPassword(userDto.getPassword().trim());
        }
        UserModel saved = userRepository.save(oldUser);
        return userMapper.toDto(saved);
    }

    public void deleteUser(String userName){
        UserModel user = userRepository.findByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteByUserName(user.getUserName());
    }
}
