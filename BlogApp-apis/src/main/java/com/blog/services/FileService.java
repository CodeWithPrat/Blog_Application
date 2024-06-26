package com.blog.services;

import java.io.FileNotFoundException; // Exception thrown when a file is not found
import java.io.IOException; // Exception thrown when an I/O operation fails or is interrupted
import java.io.InputStream; // Represents an input stream of bytes

import org.springframework.web.multipart.MultipartFile; // Represents a file uploaded in a multipart request

public interface FileService {
    
    /**
     * Uploads an image file to the specified path.
     * 
     * @param path The directory path where the file should be uploaded.
     * @param file The MultipartFile object representing the image file.
     * @return The name of the uploaded file.
     * @throws IOException If an I/O error occurs during file upload.
     */
    String uploadImage(String path, MultipartFile file) throws IOException;
    
    /**
     * Retrieves a file as an InputStream from the specified path.
     * 
     * @param path The directory path where the file is located.
     * @param fileName The name of the file to be retrieved.
     * @return An InputStream representing the file.
     * @throws FileNotFoundException If the specified file is not found.
     */
    InputStream getResource(String path, String fileName) throws FileNotFoundException;
}
