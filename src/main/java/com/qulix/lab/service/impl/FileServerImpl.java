package com.qulix.lab.service.impl;

import com.qulix.lab.entity.FileAttribute;
import com.qulix.lab.entity.FileEntity;
import com.qulix.lab.service.PropertiesService;
import com.qulix.lab.service.FileServer;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MultivaluedMap;
import java.io.*;
import java.util.*;

public class FileServerImpl implements FileServer {
    private String rootPath;

    //get root folder from properties
    {
        PropertiesService propertiesService = new PropertiesService();
        // first variant
//        rootPath = propertiesService.getRootPathWithUtilProp();
        // second variant
        rootPath = propertiesService.getRootPathWithRestEasyProp();
    }

    public FileEntity getFiles(String path) throws Exception {
        //get file with this union path
        File file = new File(rootPath + path);
        if (file.exists()) {
            // initialize FileEntity object
            FileEntity fileInfo = new FileEntity();
            //transfer file data in object
            fileInfo.setName(file.getName());
            fileInfo.setLength(file.length());
            fileInfo.setPath(path);
            //find suitable attribute
            List<FileAttribute> fileAttributes = new ArrayList<>();
            if (file.isDirectory()) {
                fileAttributes.add(FileAttribute.DIRECTORY);

                fileInfo.setInnerFiles(file.list());
            } else {
                fileAttributes.add(FileAttribute.FILE);
            }
            if (file.isHidden()) {
                fileAttributes.add(FileAttribute.HIDDEN);
            }
            if (file.canExecute()) {
                fileAttributes.add(FileAttribute.EXECUTABLE);
            }
            if (file.canWrite()) {
                fileAttributes.add(FileAttribute.WRITABLE);
            }
            if (file.canRead()) {
                fileAttributes.add(FileAttribute.READABLE);
            }
            fileInfo.setFileAttributes(fileAttributes.toArray(new FileAttribute[fileAttributes.size()]));

            return fileInfo;
        } else {
            throw new FileNotFoundException("File not found");
        }
    }

    public boolean createFolder(String path, String name){
        File file = new File(rootPath + path + "\\" + name);
        if (file.exists()) {
            throw new RuntimeException("Folder exist");
        }
        return file.mkdir();
    }
    public boolean deleteFolder(String path, String name){
        File file = new File(rootPath + path + "\\" + name);
        if (!file.exists()) {
            throw new RuntimeException("Folder doesn't exist");
        }
        return file.delete();
    }
    public boolean uploadFileWithOnlyPath(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist");
        }
        File newFile = new File(rootPath + "\\" + file.getName());

        copyDataFileInAnotherFile(file, newFile);
        return true;

    }

    public void copyDataFileInAnotherFile(File file, File anotherFile) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = new FileOutputStream(anotherFile);
        outputStream.write(inputStream.readAllBytes());
    }

    public String uploadFile(MultipartFormDataInput input){//, @QueryParam("path") String path){
        String fileName = "";

        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
//        String[] split = path.split("\\\\");
//        String s = split[split.length - 1];

        Collection<List<InputPart>> values = uploadForm.values();
        List<InputPart> inputParts = null;
        for (List<InputPart> value : values) {
            inputParts = value;
        }
//        List<InputPart> inputParts = uploadForm.get("uploadedFile");

        for (InputPart inputPart : inputParts) {

            try {

                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);

                //convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class,null);

                byte [] bytes = IOUtils.toByteArray(inputStream);

                //constructs upload file path
                fileName = rootPath + "\\" + fileName;

                writeFile(bytes,fileName);

                System.out.println("Done");

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return fileName;
    }

    /**
     * header sample
     * {
     * 	Content-Type=[image/png],
     * 	Content-Disposition=[form-data; name="file"; filename="filename.extension"]
     * }
     **/
    //get uploaded filename, is there a easy way in RESTEasy?
    private String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {

                String[] name = filename.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    //save to somewhere
    private void writeFile(byte[] content, String filename) throws IOException {

        File file = new File(filename);

        if (!file.exists()) {
            file.createNewFile();
        }

        FileOutputStream fop = new FileOutputStream(file);

        fop.write(content);
        fop.flush();
        fop.close();

    }
    public boolean deleteFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("File doesn't exist");
        }
        return file.delete();
    }
}
