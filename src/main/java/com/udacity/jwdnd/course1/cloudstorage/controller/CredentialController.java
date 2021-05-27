package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.service.ResponseService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.service.ValidationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**********************************************************************************************************************
 * Handles client requests to edit or delete login credentials.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Controller
@RequestMapping("/credentials")
public class CredentialController {
    private final UserService userService;
    private final ValidationService validationService;
    private final CredentialService credentialService;
    private final ResponseService responseService;
    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialController.class.getName());

    public CredentialController(UserService userService, ValidationService validationService,
                                CredentialService credentialService, ResponseService responseService) {
        this.userService = userService;
        this.validationService = validationService;
        this.credentialService = credentialService;
        this.responseService = responseService;
    }

    @PostMapping()
    public String editCredentials(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        User user = getCurrentUser(authentication);

        boolean result;
        if (credential.getCredentialId() == null) {
            credential.setUserId(user.getUserId());
            result = credentialService.insertCredential(credential);
        } else {
            Credential existingCredential = credentialService.getCredential(credential.getCredentialId());
            result = validationService.validate(existingCredential, user, model, "update");
            if (result) {
                //credential.setUserId(user.getUserId());
                result = credentialService.updateCredentials(credential);
            }
        }

        return responseService.createResponse(result, model, user, credentialService);
    }

    @GetMapping("/decrypt")
    public void doGet(HttpServletResponse response, Authentication authentication,
                      @ModelAttribute Credential credential) throws IOException {
        User user = getCurrentUser(authentication);
        credential = credentialService.getCredential(credential.getCredentialId());

        boolean result = validationService.validate(credential, user);
        if (result) {
            String decryptedPassword = credentialService.decryptPassword(credential);

            try (PrintWriter out = response.getWriter()) {
                out.println(decryptedPassword);
                out.flush();
            }
        }
    }

    @GetMapping("/delete")
    public String deleteCredentials(Authentication authentication, @ModelAttribute Credential credential, Model model) {
        User user = getCurrentUser(authentication);
        credential = credentialService.getCredential(credential.getCredentialId());

        boolean result = validationService.validate(credential, user, model, "delete");
        if (result) {
            result = credentialService.deleteCredentials(credential);
        }

        return responseService.createResponse(result, model, user, credentialService);
    }

    private User getCurrentUser(Authentication authentication) {
        return userService.getUser(authentication.getName());
    }
}
