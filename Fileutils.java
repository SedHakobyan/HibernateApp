package com.bee.am;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Fileutils {
    public Fileutils() throws Exception{
        LocalDate currentDate = LocalDate.now();
        foldername=currentDate.toString();
    }
    public void _fileAttachmentProcess(File file) {

        log.info("File Attachment  Process is starting!!!" + "Source from = " +file.getPath());
        _createlocaldir(remoteFolder + foldername);
        Path sourceFile = Paths.get(file.getPath());
        Path targetFile = Paths.get(remoteFolder+foldername +"\\"+file.getName());
        System.out.println("path name = " + targetFile.toString());
        try {
            Files.copy(sourceFile, targetFile,
                    StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            System.err.format("I/O Error when copying file");
        }
        movetoArchive(file);
    }
    private void movetoArchive(File file)
    {
        log.info("File Attachment  Process is starting!!!" + "Source from = " +file.getPath());
        Path sourceP =  Paths.get(file.getPath());
        _createlocaldir(localFolder+"Archive");
        LocalDate currentDate = LocalDate.now();
        String foldername=currentDate.toString().replaceAll("-","");
        File nfile=new File(localFolder+"\\"+"Archive"+"\\"+foldername+"\\",file.getName());
        Path destP = Paths.get(nfile.getPath());
        _createlocaldir(localFolder+"Archive"+"\\"+foldername);
        try {
            Files.move(sourceP, destP, StandardCopyOption.REPLACE_EXISTING);
            log.info("send file " + file.getName() +" to archive");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    private void _createlocaldir(String dirname) {
        File dir = new File(dirname);
        if(!dir.exists()){
            boolean isDirCreated = dir.mkdir();
            if(isDirCreated)
                System.out.println(dirname + " Directory created successfully ...");
            else
                System.out.println("Failed to create directory");
        }
    }
    protected static ResourceBundle ftpResources = ResourceBundle.getBundle("init");
    private static String remoteFolder = ftpResources.getString("remoteFolder");
    private static String localFolder  = ftpResources.getString("localFolder");
    private String  foldername;
    private static final Logger log = Logger.getLogger(Fileutils.class);
    public static void main(String[] args) {
        try {
            File old = new File("C:\\Users\\sedhakobyan\\Desktop\\temp\\741022.txt");
            Fileutils ft =new Fileutils();
            ft._fileAttachmentProcess(old);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    }