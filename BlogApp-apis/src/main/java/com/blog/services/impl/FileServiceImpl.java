package com.blog.services.impl;

import java.io.File; // Provides an abstraction for file and directory pathnames
import java.io.FileInputStream; // Reads bytes from a file
import java.io.FileNotFoundException; // Signals that an attempt to open the file denoted by a specified pathname has failed
import java.io.IOException; // Signals that an I/O exception of some sort has occurred
import java.io.InputStream; // Represents an input stream of bytes
import java.nio.file.Files; // Provides methods to operate on files, directories, and symbolic links
import java.nio.file.Paths; // Provides operations on paths in the file system
import java.util.UUID; // Represents a universally unique identifier (UUID)

import org.springframework.stereotype.Service; // Indicates that an annotated class is a "service"
import org.springframework.web.multipart.MultipartFile; // Represents a file received in a multipart request

import com.blog.services.FileService; // Interface defining file-related operations

@Service // Indicates that this class is a Spring service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        // Get the original filename of the uploaded file
        String originalName = file.getOriginalFilename();
        
        // Generate a random UUID for filename uniqueness
        String randomId = UUID.randomUUID().toString();
        
        // Concatenate UUID with file extension to form unique filename
        String fileName = randomId.concat(originalName.substring(originalName.lastIndexOf(".")));
        
        // Construct full file path
        String filePath = path + File.separator + fileName;
        
        // Create directory if it doesn't exist
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdir();
        }
        
        // Copy the content of the uploaded file to the specified path
        Files.copy(file.getInputStream(), Paths.get(filePath));
        
        // Return the generated unique filename
        return fileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        // Construct full path to the resource file
        String fullPath = path + File.separator + fileName;
        
        // Create an input stream for the resource file
        InputStream inputStream = new FileInputStream(fullPath);
        
        // Return the input stream
        return inputStream;
    }

}
