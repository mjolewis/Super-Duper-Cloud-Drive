package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**********************************************************************************************************************
 * This controller handles signup requests. It depends on the userService to check if the username provided already
 * exists. If not, then it tells the userService to create a new user record.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Controller
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute User user, Model model) {
        String signupError = null;

        if (!userService.isUserNameAvailable(user.getUsername())) {
            signupError = "This username already exists.";
        }

        if (signupError == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupError = "There was an error signing up. Please try again.";
            }
        }

        if (signupError == null) {
            model.addAttribute("signupSuccess", true);
        } else  {
            model.addAttribute("signupError", signupError);
        }

        return "signup";
    }
}
