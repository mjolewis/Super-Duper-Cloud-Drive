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
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public ArrayList<Credential> getCredentials(User user) {
        return credentialMapper.getCredentials(user);
    }

    public Credential getCredential(Integer credentialId) {
        return credentialMapper.getCredential(credentialId);
    }

    public boolean insertCredential(Credential credential) {
        return credentialMapper.insertCredential(encryptPassword(credential)) > 0;
    }

    public boolean updateCredentials(Credential credential) {
        return credentialMapper.updateCredential(encryptPassword(credential)) == 1;
    }

    private Credential encryptPassword(Credential credential) {
        if (credential.getKey() == null) {
            String key = encryptionService.generateKey();
            credential.setKey(key);
        }

        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), credential.getKey());
        credential.setPassword(encryptedPassword);
        return credential;
    }

    public String decryptPassword(Credential credential) {
        return encryptionService.decryptValue(credential.getPassword(), credential.getKey());
    }

    public boolean deleteCredentials(Credential credential) {
        return credentialMapper.deleteCredential(credential) == 1;
    }

    @Override
    public String getServiceType() {
        return "credentials";
    }
}
