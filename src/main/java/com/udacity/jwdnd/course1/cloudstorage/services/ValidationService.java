package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.StoredObject;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**********************************************************************************************************************
 * Validates user requests before sending those requests to the server layer.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Service
public class ValidationService {
    public boolean validate(StoredObject obj, User user, Model model, String crud) {
        String message = null;
        boolean result = true;

        if (obj == null) {
            message = "File not found.";
        } else if (obj.getUserId().intValue() != user.getUserId().intValue()) {
            message = "Error. You don't have permission to " + crud + " this " + obj.getType() + " .";
        }

        if (message != null) {
            model.addAttribute("message", message);
            result = false;
        }

        return result;
    }
}
