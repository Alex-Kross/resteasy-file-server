package com.qulix.lab.controller;

import ch.qos.logback.classic.Logger;
import com.qulix.lab.service.impl.FileServerImpl;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;
import java.util.Properties;

@Path("/file-system")
public class FileServerController {
    private static final Logger logger
            = (Logger) LoggerFactory.getLogger(FileServerController.class);
    private FileServerImpl fileServerService = new FileServerImpl();

    @GET
    @Path("/mpinfo")
    @Produces(MediaType.APPLICATION_JSON)
    @Timed(name = "getMPMetricsInfoTimed", description = "Metrics to monitor the times spent in getMPMetricsInfo method", unit = MetricUnits.SECONDS, absolute = true)
    @Counted(description = "counter of the getMPMetricsInfo method", absolute = true)
    public String getMPMetricsInfo() {
        return "MicrpProfle Metrics API 2.3";
    }
    @GET
    @Path("/files")
    @Operation(operationId = "hello world")
    @Produces("application/xml")
    public String getFiles(@QueryParam("path") String path){
        String answer = "";
        Properties properties = System.getProperties();
        try {
            answer = fileServerService.getFiles(path).toString();
//            logger.info("request for get all files is success");
        } catch (FileNotFoundException e) {
//            logger.error("request for get all files is fail: " + e);
            answer = e.getMessage();
        } catch (Exception e) {
            answer = e.getMessage();
//            logger.error("request for get all files is fail: " + e);
        }
        return answer;
    }

    @GET
    @Path("/create-folder")
    @Produces("application/xml")
    public String createFolder(@QueryParam("path") String path, @QueryParam("name") String name) {
//        logger.info("request for create the folder");
        return String.valueOf(fileServerService.createFolder(path, name));
    }

    @GET
    @Path("/delete-folder")
    @Produces("application/xml")
    public String deleteFolder(@QueryParam("path") String path, @QueryParam("name") String name) {
//        logger.info("request for delete the folder");
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

//    private final String UPLOADED_FILE_PATH = "C:\\Super\\";

    @POST
    @Path("upload")
    @Consumes("multipart/form-data")
    public Response uploadFile(MultipartFormDataInput input, @QueryParam("path") String path) {
        String fileName = fileServerService.uploadFile(input, path);
//        logger.info("request for upload files on server");
        return Response.status(200)
                .entity("uploadFile is called, Uploaded file name : " + fileName).build();
    }

    @DELETE
    @Path("delete")
    @Produces("application/xml")
    public String deleteFile(@QueryParam("path") String path) {
//        logger.info("request for delete the file from server");
        return String.valueOf(fileServerService.deleteFile(path));
    }

    @GET
    @Path("download")
    public Response downloadFile(@QueryParam("path") String path){
        File file = fileServerService.downloadFile(path);
        String[] split = file.getAbsolutePath().split("\\\\");
        String fileName = split[split.length - 1];
        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename=" + fileName);
//        logger.info("request for download the file from server");
        return response.build();
    }
}
