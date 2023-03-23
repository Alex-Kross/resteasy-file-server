package com.qulix.lab;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
@Path("/hello")
public class FileServerController {
    @GET
    @Path("/message")
    @Produces("application/xml")
    public String showMessage() {
        return "Hello world!";
    }
}
