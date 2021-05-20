package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**********************************************************************************************************************
 * Interface for file CRUD operations.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    ArrayList<SuperDuperFile> getFiles(User userId);

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    SuperDuperFile getFile(Integer fileId);

    @Select("SELECT COUNT(*) FROM FILES WHERE filename = #{fileName} AND userid = #{userId}")
    int countFiles(SuperDuperFile file);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insertFile(SuperDuperFile file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    int deleteFile(Integer fileId);

    @Delete("DELETE FROM FILE WHERE userid = #{userId}")
    int deleteFiles(User user);
}