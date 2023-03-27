package com.qulix.lab.controller;

import com.qulix.lab.service.impl.FileServerService;

import javax.ws.rs.*;
import java.io.FileNotFoundException;

@Path("/file-system")
public class FileServerController {
    private FileServerService fileServerService = new FileServerService();
    @GET
    @Path("/files")
    @Produces("application/xml")
    public String getFiles(@QueryParam("path") String path) {
        String answer = "";
        try {
            answer = fileServerService.getFiles(path).toString();
        } catch (FileNotFoundException e) {
            answer = e.getMessage();
        } catch (Exception e) {
            answer = e.getMessage();
        }
        return answer;
    }
    @GET
    @Path("/create-folder")
    @Produces("application/xml")
    public String createFolder(@QueryParam("path") String path, @QueryParam("name") String name){
        return String.valueOf(fileServerService.createFolder(path, name));
    }
    @GET
    @Path("/delete-folder")
    @Produces("application/xml")
    public String deleteFolder(@QueryParam("path") String path, @QueryParam("name") String name){
        return String.valueOf(fileServerService.deleteFolder(path, name));
    }

}
