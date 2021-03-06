package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.SuperDuperFile;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.tomcat.util.http.fileupload.InvalidFileNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

/**********************************************************************************************************************
 * This service handles file storage events. The FileUploadController is dependent on the StorageService and the
 * StorageService is dependent on the StorageMapper.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
@Service
public class FileService implements ServiceType {
    private final Logger logger = LoggerFactory.getLogger(FileService.class);
    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public SuperDuperFile prepareFileForUpload(MultipartFile multipartFile, User user) {
        SuperDuperFile superDuperFile = null;

        try {
            superDuperFile = new SuperDuperFile(null,
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    String.valueOf(multipartFile.getSize()),
                    user.getUserId(),
                    multipartFile.getBytes());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }

        return superDuperFile;
    }

    public boolean insertFile(SuperDuperFile superDuperFile) throws InvalidFileNameException {
        if (fileMapper.countFiles(superDuperFile) > 0) {
            throw new InvalidFileNameException(
                    superDuperFile.getFileName(), "Error. File already exists. Cannot be inserted again.");
        }

        return fileMapper.insertFile(superDuperFile) == 1;
    }

    public ArrayList<SuperDuperFile> getFiles(User user) {
        return fileMapper.getFiles(user);
    }

    public SuperDuperFile getFile(int fileId) {
        return fileMapper.getFile(fileId);
    }

    public boolean deleteFile(SuperDuperFile file) {
        return fileMapper.deleteFile(file.getFileId()) == 1;
    }

    @Override
    public String getServiceType() {
        return "files";
    }
}
