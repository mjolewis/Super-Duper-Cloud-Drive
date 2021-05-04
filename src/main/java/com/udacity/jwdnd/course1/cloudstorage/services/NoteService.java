package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**********************************************************************************************************************
 * This service handles note storage events. The FileUploadController is dependent on the StorageService and the
 * StorageService is dependent on the StorageMapper.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Service
public class NoteService {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public ArrayList<Note> getNotes(User user) {
        return noteMapper.getNotes(user);
    }
}
