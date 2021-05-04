package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**********************************************************************************************************************
 * Interface for note CRUD operations.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM notes WHERE userid = #{userId}")
    ArrayList<Note> getNotes(User user);

    @Select("SELECT * FROM notes WHERE noteid = #{noteId}")
    Note getNote(Integer noteId);

    @Insert("INSERT INTO notes (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insert(Note note);

    @Update("UPDATE notes SET notetitle = #{noteTitle}, notedescription = #{noteDescription}, userid = #{userId} WHERE noteid = #{noteId}")
    int update(Note note);

    @Delete("DELETE FROM notes WHERE userid = #{userId}")
    int deleteNotes(User user);

    @Delete("DELETE FROM notes WHERE noteid = #{noteId}")
    int delete(Note note);
}