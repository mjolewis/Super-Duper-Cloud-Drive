package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.FileService;
import com.udacity.jwdnd.course1.cloudstorage.service.ResponseService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.service.ValidationService;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.text.StringEscapeUtils;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**********************************************************************************************************************
 * The FileUploadController handles client requests to insert and delete files.
 *
 * @note Max file size of 5MB.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Controller
@RequestMapping("/files")
public class FileUploadController {
    private final UserService userService;
    private final FileService fileService;
    private final ValidationService validationService;
    private final ResponseService responseService;

    public FileUploadController(UserService userService, FileService fileService,
                                ValidationService validationService, ResponseService responseService) {
        this.userService = userService;
        this.fileService = fileService;
        this.validationService = validationService;
        this.responseService = responseService;
    }

    @PostMapping()
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile multiPartFile, Model model) {
        User user = getCurrentUser(authentication);

        // Prevent users from uploading an empty file.
        if (multiPartFile.isEmpty()) {
            return responseService.createUploadFailed(true, model, fileService);
        } else {
            SuperDuperFile superDuperFile = fileService.prepareFileForUpload(multiPartFile, user);
            boolean result = validationService.validate(superDuperFile, user, model, "insert");
            if (result) {
                try {
                    result = fileService.insertFile(superDuperFile);
                } catch (InvalidFileNameException e) {
                    result = false;
                    model.addAttribute("message", e.getMessage());
                }
            }

            return responseService.createResponse(result, model, user, fileService);
        }
    }

    @GetMapping("/download")
    public void downloadFile(HttpServletResponse response, Authentication authentication,
                             @ModelAttribute SuperDuperFile file, Model model) throws IOException {

        User currentUser = userService.getUser(authentication.getName());
        file = fileService.getFile(file.getFileId());

        boolean result = validationService.validate(file, currentUser, model, "download");

        if (result) {
            response.setContentType(file.getContentType());
            response.setContentLength(Integer.parseInt(file.getFileSize()));
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=" + StringEscapeUtils.escapeHtml4(file.getFileName());
            response.setHeader(headerKey, headerValue);
            response.getOutputStream().write(file.getFileData());
        }
    }

    @GetMapping("/delete")
    public String deleteFile(Authentication authentication, @ModelAttribute SuperDuperFile file, Model model) {
        file = fileService.getFile(file.getFileId());
        User user = userService.getUser(authentication.getName());

        boolean result = validationService.validate(file, user, model, "delete");

        if (result) {
            result = fileService.deleteFile(file);
        }

        return responseService.createResponse(result, model, user, fileService);
    }

    private User getCurrentUser(Authentication authentication) {
        return userService.getUser(authentication.getName());
    }
}
