package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**********************************************************************************************************************
 * Interface for credential CRUD operations.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM credentials WHERE userid = #{userId}")
    ArrayList<Credential> getCredentials(User user);

    @Select("SELECT * FROM credentials WHERE credentialid = #{credentialId}")
    Credential getCredential(Integer credentialId);

    @Insert("INSERT INTO credentials (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Update("UPDATE credentials SET url = #{url}, username = #{username}, key = #{key}, password = #{password} WHERE credentialid = #{credentialId}")
    int updateCredential(Credential credential);

    @Delete("DELETE FROM credentials WHERE credentialid = #{credentialId}")
    int deleteCredential(Credential credential);
}