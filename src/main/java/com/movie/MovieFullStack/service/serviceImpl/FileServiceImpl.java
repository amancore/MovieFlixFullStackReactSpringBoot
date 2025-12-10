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
        // 1. get the name of the file
        String originalName = file.getOriginalFilename();
        // 2. Make filename safe (small change here)
        assert originalName != null;
        String safeName = originalName.replaceAll("[^a-zA-Z0-9.]", "_");

        // 3. these two things needs to get appended
        String filePath = path + File.separator + safeName;

        // 4. Create a file obj and copy the file to that file that we are getting
        // this is responsible for where the path exists or not
        // Create folder if not exists if not then create it
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir(); // posters
        }

        // 5. copy the file or upload the file to the path | filePath is actual path of the file where we need to upload the file
        // StandardCopyOption -> if a file with same name already exists -> replace it
        Files.copy(
                file.getInputStream(),
                Paths.get(filePath),
                StandardCopyOption.REPLACE_EXISTING
        );
        // 6. Return safe filename
        return safeName;
    }

    @Override
    public InputStream getResourceFile(String path, String fileName) throws FileNotFoundException {
        String filePath = path + File.separator + fileName;
        return new FileInputStream(filePath);
    }
}
