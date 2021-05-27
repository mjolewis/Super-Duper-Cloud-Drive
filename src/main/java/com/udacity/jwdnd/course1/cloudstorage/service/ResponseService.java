package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**********************************************************************************************************************
 * A service that composes a response for the template layer. The core controllers depend on this service to create a
 * response.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Service
public class ResponseService {

    public String createResponse(boolean result, Model model, User user, FileService service) {
        model.addAttribute(service.getServiceType(), service.getFiles(user));
        return createResponse(result, model, service.getServiceType());
    }

    public String createResponse(boolean result, Model model, User user, NoteService service) {
        model.addAttribute(service.getServiceType(), service.getNotes(user));
        return createResponse(result, model, service.getServiceType());
    }

    public String createResponse(boolean result, Model model, User user, CredentialService service) {
        model.addAttribute(service.getServiceType(), service.getCredentials(user));
        return createResponse(result, model, service.getServiceType());
    }

    private String createResponse(boolean result, Model model, String serviceType) {
        model.addAttribute("success", result);
        model.addAttribute("applicationEdgeCaseErrorMessage", false);
        model.addAttribute("nav", "/home#nav-" + serviceType);
        return "result";
    }

    public String createUploadFailed(boolean result, Model model, FileService service) {
        model.addAttribute("uploadFailed", result);
        model.addAttribute("applicationEdgeCaseErrorMessage", true);
        model.addAttribute("nav", "/home#nav-" + service.getServiceType());
        return "result";
    }

    public String createExceedCharacterLimitResponse(boolean result, Model model, NoteService service) {
        model.addAttribute("characterLimit", result);
        model.addAttribute("applicationEdgeCaseErrorMessage", true);
        model.addAttribute("nav", "/home#nav-" + service.getServiceType());
        return "result";
    }

    public String createExceedFileSizeError(boolean result, Model model) {
        model.addAttribute("fileSizeExceeded", result);
        model.addAttribute("applicationEdgeCaseErrorMessage", true);
        return "result";
    }
}
