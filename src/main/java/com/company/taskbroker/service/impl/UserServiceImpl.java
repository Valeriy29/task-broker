package com.company.taskbroker.service.impl;

import com.company.taskbroker.model.Role;
import com.company.taskbroker.model.Status;
import com.company.taskbroker.model.User;
import com.company.taskbroker.repository.RoleRepository;
import com.company.taskbroker.repository.UserRepository;
import com.company.taskbroker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");

        user.setRoles(List.of(roleUser));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(Status.ACTIVE);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        User registeredUser = userRepository.save(user);

        log.info("IN register - user: {} successfully registered", registeredUser );
        return registeredUser;
    }

    @Override
    public List<User> getAll() {
        List<User> users = userRepository.findAll();

        log.info("IN getALL - {} users found", users.size());
        return users;
    }

    @Override
    public User findByUserName(String username) {
        User user = userRepository.findUserByUsername(username);

        log.info("IN findByUserName - user: {} found by username {}", user, username);
        return user;
    }

    @Override
    public User findById(Long id) {
        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            log.warn("IN findById - no user found by ud {}", id);
            return null;
        }

        log.info("IN findById - user: {} found by ud {}", user, id);
        return user;
    }

    @Override
    public void delete(Long id) {
        userRepository.deleteById(id);
        log.info("IN findById - user with id: {} successfully deleted", id);
    }
}
