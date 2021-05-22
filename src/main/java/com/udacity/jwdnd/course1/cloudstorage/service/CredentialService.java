package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**********************************************************************************************************************
 * Service layer that allows clients to modify login credentials.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Service
public class CredentialService implements ServiceType {
    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public ArrayList<Credential> getCredentials(User user) {
        return credentialMapper.getCredentials(user);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public boolean insertCredential(Credential credential) {
        return credentialMapper.insertCredential(credential) > 0;
    }

    public boolean updateCredentials(Credential credential) {
        return credentialMapper.updateCredential(credential) == 1;
    }

    public String decryptPassword(Credential credential) {
        return credentialMapper.getCredential(credential.getCredentialId()).getPassword();
    }

    public boolean deleteCredentials(Credential credential) {
        return credentialMapper.deleteCredential(credential) == 1;
    }

    @Override
    public String getServiceType() {
        return "credentials";
    }
}
