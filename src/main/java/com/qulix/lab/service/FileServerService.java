package com.qulix.lab.service;

import com.qulix.lab.entity.FileAttribute;
import com.qulix.lab.entity.FileEntity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileServerService {

//    String rootPath = System.getProperty("user.dir");
    String rootPath;
    List<FileEntity> listFiles = new ArrayList<>();
    public FileEntity getFiles(String path) throws Exception {
        File file;
        //configure root directory
        System.setProperty("user.dir", "C:\\Users\\KarpukAU\\untitled");
        rootPath = System.getProperty("user.dir");

        file = new File(rootPath + path);
        if (file.exists()) {
            // initialize FileEntity object
            FileEntity fileInfo = new FileEntity();


            fileInfo.setName(file.getName());
            fileInfo.setLength(file.length());
            fileInfo.setPath(path);
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
