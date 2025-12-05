package com.movie.MovieFullStack.service.serviceImpl;

import com.movie.MovieFullStack.repository.MovieRepository;
import com.movie.MovieFullStack.service.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {
    private MovieRepository movieRepository;

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        // get name of the file
        String fileName = file.getOriginalFilename();

        // to get the file path
        // these two things needs to get appended
        String filePath = path + File.separator + fileName;

        // Create a file object and cpy the file to that file that we are getting
        // this is responsible for where tha path exists or not
        File f = new File(path);  // checking if the folder already exits
        if(!f.exists()){  // if folder don't exists create it
            f.mkdir(); // posters
        }
        // copy the file or upload the file to the path
        // filePath is acutal path of the file where we need to upload the file
        // StandardCopyOption -> If a file with same name already exists → replace it.
        Files.copy(
                file.getInputStream(),
                Paths.get(filePath),
                StandardCopyOption.REPLACE_EXISTING
        );
        return fileName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
