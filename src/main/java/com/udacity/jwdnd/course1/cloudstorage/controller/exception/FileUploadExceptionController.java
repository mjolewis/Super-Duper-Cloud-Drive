package com.udacity.jwdnd.course1.cloudstorage.controller.exception;

import com.udacity.jwdnd.course1.cloudstorage.service.ResponseService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**********************************************************************************************************************
 * The FileUploadExceptionController class provides custom html page for events causing an HTTP 403 error.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@ControllerAdvice
public class FileUploadExceptionController {
    private final ResponseService responseService;

    public FileUploadExceptionController(ResponseService responseService) {
        this.responseService = responseService;
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(Model model) {
        return responseService.createExceedFileSizeError(true, model);
    }
}