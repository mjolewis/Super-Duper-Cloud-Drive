package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.model.CloudStorageServiceObjet;
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
    public boolean validate(CloudStorageServiceObjet obj, User user, Model model, String crud) {
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

    public boolean validate(CloudStorageServiceObjet obj, User user) {
        boolean result = true;

        if (obj == null) {
            result = false;
        } else if (obj.getUserId().intValue() != user.getUserId().intValue()) {
            result = false;
        }

        return result;
    }
}
