package com.qulix.lab.service;

import com.qulix.lab.entity.FileEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileServerServiceTest {

    @Test
    void getFile() {
        FileServerService service = new FileServerService();
        try {
            String actual = service.getFiles("").toString();
            String expected = "name = Users\n" +
                    "path = \n" +
                    "length = 4096\n" +
                    "fileAttributes = DIRECTORY, EXECUTABLE, WRITABLE, READABLE, \n" +
                    "innerFiles = Administrator, All Users, Default, Default User, desktop.ini, KarpukAU, Public, ";

            Assertions.assertEquals(expected, actual);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
    @Test
    void getChangingFile() {
        FileServerService service = new FileServerService();
        try {

            String actual = service.getFiles("/.gitignore").toString();
            String expected = "name = .gitignore\n" +
                    "path = /.gitignore\n" +
                    "length = 5\n" +
                    "fileAttributes = FILE, EXECUTABLE, WRITABLE, READABLE, \n" +
                    "innerFiles = none";
            Assertions.assertEquals(expected, actual);
//            System.setProperty()
        } catch (Exception e){

        }
    }
}