package com.qulix.lab;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
@ApplicationPath("/rws")
public class RestEasyApplication extends Application
{
    private Set<Class> classes = new HashSet<Class>();

    public RestEasyApplication() {
        classes.add(FileServerController.class);
    }

    @Override
    public Set<Class<?>> getClasses() {
        return super.getClasses();
    }
}
