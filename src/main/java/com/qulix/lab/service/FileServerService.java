package com.qulix.lab.service;

import com.qulix.lab.entity.FileAttribute;
import com.qulix.lab.entity.FileEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileServerService {

//    String rootPath = System.getProperty("user.dir");
    String rootPath;
    public FileEntity getFiles(String path) throws Exception {
        File file;
        //get root folder from properties
        PropertiesService propertiesService = new PropertiesService();
        // first variant
//        rootPath = propertiesService.getRootPathWithUtilProp();
        // second variant
        rootPath = propertiesService.getRootPathWithRestEasyProp();

        //get file with this union path
        file = new File(rootPath + path);
        if (file.exists()) {
            // initialize FileEntity object
            FileEntity fileInfo = new FileEntity();
            //transfer file data in object
            fileInfo.setName(file.getName());
            fileInfo.setLength(file.length());
            fileInfo.setPath(path);
            //find suitable attribute
            List<FileAttribute> fileAttributes = new ArrayList<>();
            if (file.isDirectory()) {
                fileAttributes.add(FileAttribute.DIRECTORY);

                fileInfo.setInnerFiles(file.list());
            } else {
                fileAttributes.add(FileAttribute.FILE);
            }
            if (file.isHidden()) {
                fileAttributes.add(FileAttribute.HIDDEN);
            }
            if (file.canExecute()) {
                fileAttributes.add(FileAttribute.EXECUTABLE);
            }
            if (file.canWrite()) {
                fileAttributes.add(FileAttribute.WRITABLE);
            }
            if (file.canRead()) {
                fileAttributes.add(FileAttribute.READABLE);
            }
            fileInfo.setFileAttributes(fileAttributes.toArray(new FileAttribute[fileAttributes.size()]));

            return fileInfo;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }
}
