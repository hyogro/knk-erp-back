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

    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private String getOriginalFileName(MultipartFile multipartFile) {
        return multipartFile.getOriginalFilename();
    }

    private String makeFileName(String extension) {
        String fileName = LocalDateTime.now() + "";
        return fileName.replace("T", "").replace("-", "").replace(".", "").replace(":", "") + extension;
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    private void resolveFile(MultipartFile multipartFile, String fileName) throws IOException {
        Path location = this.path.resolve(fileName);
        Files.copy(multipartFile.getInputStream(), location, StandardCopyOption.REPLACE_EXISTING);
    }

    private File saveEntity(MultipartFile multipartFile) {
        String memberId = getMemberId();
        String originalFilename = getOriginalFileName(multipartFile);
        String extension = getExtension(originalFilename);
        String fileName = makeFileName(extension);

        File file = File.builder().originalFileName(originalFilename).fileName(fileName).extension(extension).memberId(memberId).build();
        return fileRepository.save(file);
    }

    public RES_fileSave saveFile(MultipartFile multipartFile) {
        try {
            String fileName = saveEntity(multipartFile).getFileName();

            resolveFile(multipartFile, fileName);

            return new RES_fileSave("FS001", fileName);
        } catch (Exception e) {
            return new RES_fileSave("FS002", e.getMessage());
        }

    }
}
