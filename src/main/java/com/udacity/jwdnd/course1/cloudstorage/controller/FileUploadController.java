package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.service.ValidationService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**********************************************************************************************************************
 *
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Controller
@RequestMapping("/files")
public class FileUploadController {
    private final UserService userService;
    private final FileService fileService;
    private final ValidationService validationService;

    public FileUploadController(UserService userService, FileService fileService, ValidationService validationService) {
        this.userService = userService;
        this.fileService = fileService;
        this.validationService = validationService;
    }

    @GetMapping("/delete")
    public String deleteFile(Authentication authentication, @ModelAttribute SuperDuperFile file, Model model) {
        file = fileService.getFile(file.getFileId());
        User user = userService.getUser(authentication.getName());

        boolean result = validationService.validate(file, user, model, "delete");

        if (result) {
            result = fileService.deleteFile(file);
        }

        return "result";
    }

    @PostMapping()
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multiPartFile, Model model) {
        User user = userService.getUser(authentication.getName());
        SuperDuperFile superDuperFile = fileService.prepareFileForUpload(multiPartFile, user);

//        UploadResponse uploadResponse = fileService.upload(superDuperFile);
//        switch (uploadResponse) {
//            case SUCCESS:
//                model.addAttribute("uploadSuccess", true);
//            case FAILURE:
//                model.addAttribute("uploadFailure", false);
//            case ERROR:
//                model.addAttribute("uploadError", "Upload error. Please try again.");
//        }

        // TODO: 5/2/21 return all files

        return "result";
    }
}
