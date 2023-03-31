package com.qulix.lab.controller;

import com.qulix.lab.service.impl.FileServerImpl;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.*;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.List;
import java.util.Map;

@Path("/file-system")
public class FileServerController {
    private FileServerImpl fileServerService = new FileServerImpl();

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
    public String createFolder(@QueryParam("path") String path, @QueryParam("name") String name) {
        return String.valueOf(fileServerService.createFolder(path, name));
    }

    @GET
    @Path("/delete-folder")
    @Produces("application/xml")
    public String deleteFolder(@QueryParam("path") String path, @QueryParam("name") String name) {
        return String.valueOf(fileServerService.deleteFolder(path, name));
    }

    @GET
    @Path("/upload/with-only-path")
    @Produces("application/xml")
    public String uploadFileWithOnlyPath(@QueryParam("path") String path) {
        try {
            return String.valueOf(fileServerService.uploadFileWithOnlyPath(path));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private final String UPLOADED_FILE_PATH = "C:\\Super\\";
    @POST
    @Path("upload")
    @Consumes("multipart/form-data")
    public Response uploadFile(MultipartFormDataInput input, @QueryParam("path") String path) {
        String fileName = fileServerService.uploadFile(input, path);
        return Response.status(200)
                .entity("uploadFile is called, Uploaded file name : " + fileName).build();
    }

    @DELETE
    @Path("delete")
    @Produces("application/xml")
    public String deleteFile(@QueryParam("path") String path) {
        return String.valueOf(fileServerService.deleteFile(path));
    }


}
