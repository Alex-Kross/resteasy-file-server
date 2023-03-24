package com.qulix.lab.controller;

import com.qulix.lab.service.FileServerService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.io.FileNotFoundException;

@Path("/hello")
public class FileServerController {
    private FileServerService fileServerService = new FileServerService();
    @GET
    @Path("/message")
    @Produces("application/xml")
    public String showMessage() {

        String answer = "";
        try {
            answer = fileServerService.getFiles("/.idea").toString();
        } catch (FileNotFoundException e) {
            answer = e.getMessage();
        } catch (Exception e) {
            answer = e.getMessage();
        }
        return answer;
    }
}
