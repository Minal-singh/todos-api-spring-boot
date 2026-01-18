package com.minal.todo.repositories;

import com.minal.todo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    Optional<UserModel> findByUserName(String userName);
    void deleteByUserName(String userName);
}
