package knk.erp.api.shlee.file.controller;

import knk.erp.api.shlee.file.service.FileService;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {
    FileService fileService;
    FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseCM> uploadFile(@RequestParam MultipartFile file) {
        if(file.isEmpty()) {
            ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(fileService.saveFile(file));
    }
}
