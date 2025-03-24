package iot.service.impl;

import iot.service.UserService;
import iot.model.User;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import iot.dao.UserDao;
import iot.middleware.JwtUtils;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtils jwtUtils;

    // Logging
    private final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Override
    public User getUserById(Long userId) {
        User user = userDao.findById(userId).orElse(null);
        if (user == null) {
            logger.error("User not found");
            return null;
        } else {
            logger.info("Fetch user successfully with username:" + userId);
            return user;
        }
    }

    @Override
    public void createUser(User user) {
        userDao.save(user);
    }

    @Override
    public void updateUser(User user) {
        userDao.save(user);
    }

    @Override
    public String verifyAccount(String username, String password) {
        // Check if user exists
        User user = userDao.findByUsername(username);
        if (user == null) {
            logger.error("User not found");
            return null;
        } else {
            logger.info("Fetch user successfully with username:" + username);
            return jwtUtils.generateToken(username);
        }
        // Generate JWT token
    }

}
