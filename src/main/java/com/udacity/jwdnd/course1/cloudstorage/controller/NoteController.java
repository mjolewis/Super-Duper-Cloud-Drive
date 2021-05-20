package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.service.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.service.ResponseService;
import com.udacity.jwdnd.course1.cloudstorage.service.UserService;
import com.udacity.jwdnd.course1.cloudstorage.service.ValidationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**********************************************************************************************************************
 * Handles client requests to insert, update, and delete notes.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Configuration
@RequestMapping("/notes")
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;
    private final ValidationService validationService;
    private final ResponseService responseService;

    public NoteController(UserService userService, NoteService noteService, ValidationService validationService,
                          ResponseService responseService) {
        this.userService = userService;
        this.noteService = noteService;
        this.validationService = validationService;
        this.responseService = responseService;
    }

    @PostMapping()
    public String editNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        User user = getCurrentUser(authentication);

        boolean result;
        if (note.getNoteId() == null) {
            note.setUserId(user.getUserId());
            result = noteService.insertNote(note);
        } else {
            Note existingNote = noteService.getNote(note.getNoteId());
            result = validationService.validate(existingNote, user, model, "update");
            if (result) {
                note.setUserId(user.getUserId());
                result = noteService.updateNote(note);
            }
        }

        return responseService.createResponse(result, model, user, noteService);
    }

    @GetMapping("/delete")
    public String deleteNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        User user = getCurrentUser(authentication);
        note = noteService.getNote(note.getNoteId());

        boolean result = validationService.validate(note, user, model, "delete");
        if (result) {
            result = noteService.deleteNote(note);
        }

        return responseService.createResponse(result, model, user, noteService);
    }

    private User getCurrentUser(Authentication authentication) {
        return userService.getUser(authentication.getName());
    }
}


