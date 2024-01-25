package com.yi.service;

import com.yi.entity.User;
import com.yi.exception.InsufficientBalanceException;
import com.yi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(Math.toIntExact(userId));
        return optionalUser.get();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(rollbackOn = {InsufficientBalanceException.class, Exception.class})
    public User updateUser(User user) throws InsufficientBalanceException {
        User existingUser = userRepository.findById(Math.toIntExact(user.getId())).get();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        User updatedUser = userRepository.save(existingUser);
        if(updatedUser.getId() == 1){
            throw new InsufficientBalanceException("잔액이 부족합니다.");
//            throw new RuntimeException("RuntimeException (Unchecked Exception)");
        }
        return updatedUser;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(Math.toIntExact(userId));
    }
}