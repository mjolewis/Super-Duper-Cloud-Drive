package com.udacity.jwdnd.course1.cloudstorage.service;

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
public class NoteService implements ServiceType {
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public ArrayList<Note> getNotes(User user) {
        return noteMapper.getNotes(user);
    }

    public Note getNote(Integer noteId) {
        return noteMapper.getNote(noteId);
    }

    public boolean insertNote(Note note) {
        return noteMapper.insertNote(note) > 0;
    }

    public boolean updateNote(Note note) {
        return noteMapper.updateNote(note) == 1;
    }

    public boolean deleteNote(Note note) {
        return noteMapper.delete(note) == 1;
    }

    @Override
    public String getServiceType() {
        return "notes";
    }
}
