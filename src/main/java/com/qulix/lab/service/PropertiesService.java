package com.qulix.lab.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesService {
    public String getRootPath(){
        try(InputStream inputStream = new FileInputStream("src/main/resources/service.properties")){
            Properties properties = new Properties();
            properties.load(inputStream);

            return properties.getProperty("path.root");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
