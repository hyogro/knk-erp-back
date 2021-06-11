package knk.erp.api.shlee.file.service;

import knk.erp.api.shlee.file.dto.RES_fileSave;
import knk.erp.api.shlee.file.entity.File;
import knk.erp.api.shlee.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final Path path = Paths.get("/home/ubuntu/files");

    private String fileNameMake(String extension){
        String fileName = LocalDateTime.now()+"";
        return fileName.replace("T", "").replace("-","").replace(".","").replace(":","")+extension;

    }

    public RES_fileSave saveFile(MultipartFile multipartFile) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String memberId = authentication.getName();

            String originalFilename = multipartFile.getOriginalFilename();
            assert originalFilename != null;

            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

            String fileName = fileNameMake(extension);

            File file = File.builder().originalFileName(originalFilename).fileName(fileName).extension(extension).memberId(memberId).build();
            fileRepository.save(file);

            Path location = this.path.resolve(fileName);
            Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
            return new RES_fileSave("FS001", fileName);
        } catch (IOException e) {
            return new RES_fileSave("FS002", e.getMessage());
        }

    }
}
