package com.qulix.lab.service;

import com.qulix.lab.service.impl.FileServerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileServerImplTest {
    private FileServerImpl service = new FileServerImpl();
    @Test
    void getFile() {
        service = new FileServerImpl();
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
    void createFolder() {
       boolean actual = service.createFolder("", "newCat");
       boolean expected = true;
       Assertions.assertEquals(expected, actual);
    }

    @Test
    void deleteFolder() {
        boolean actual = service.deleteFolder("", "newCat");
        boolean expected = true;
        Assertions.assertEquals(expected, actual);
    }
}