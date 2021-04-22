package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

/**********************************************************************************************************************
 * This service handles manages user registration events. The UserController is dependent on the UserService and the
 * UserService is dependent on the UserMapper.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    /**
     * Checks if the given username is already registered in the database. If not, then the new username can be
     * registered. If not, the server response will include a message indicated that the specified username is
     * unavailable.
     * @param username A username sent by the client.
     * @return True if the username is not in the database. Otherwise, false.
     */
    public boolean isUserNameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    /**
     * Register a new user into the database. The system never stores the plain text password. Instead, it stores
     * a salted hash of the user supplied password.
     * @param user The form backing object of Signup.html
     * @return The autogenerated primary key of the newly registered user.
     */
    public int createUser(User user) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        return userMapper.insert(new User(user.getUserId(), user.getUsername(),
                encodedSalt, hashedPassword, user.getFirstname(), user.getLastname()));
    }

    public User getUser(String username) {
        return userMapper.getUser(username);
    }
}
