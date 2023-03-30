package com.qulix.lab.service;

import com.qulix.lab.service.impl.FileServerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

class FileServerImplTest {
    private FileServerImpl service = new FileServerImpl();
    PropertiesService propertiesService = new PropertiesService();
    private String rootPath = propertiesService.getRootPathWithRestEasyProp();
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
    @Test
    void uploadFileWithOnlyPath() {
        File file = new File("C:\\Users\\KarpukAU\\image.jpg");
        File newFile = new File(rootPath + "\\" + file.getName());
        byte[] expectedData;
        byte[] actualData;
        try {
            FileInputStream expectedInputStream = new FileInputStream(file);
            expectedData = expectedInputStream.readAllBytes();
            service.copyDataFileInAnotherFile(file, newFile);
            FileInputStream actualInputStream = new FileInputStream(file);
            actualData = actualInputStream.readAllBytes();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Assertions.assertArrayEquals(expectedData, actualData);
    }

    @Test
    void deleteFile() {
        String path = rootPath + "\\" + ".gitignore";
        if (new File(path).exists()) {
            boolean actual = service.deleteFile(path);
            boolean expected = true;
            Assertions.assertEquals(expected, actual);
        }
    }
}