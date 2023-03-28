package com.qulix.lab.service;

import com.qulix.lab.entity.FileEntity;

public interface FileServer {
    FileEntity getFiles(String path) throws Exception;
    boolean createFolder(String path, String name);
    boolean deleteFolder(String path, String name);
}
