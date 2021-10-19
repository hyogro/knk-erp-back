package knk.erp.api.shlee.domain.file.controller;

import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.file.service.FileService;
import knk.erp.api.shlee.domain.schedule.responseEntity.ResponseCM;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@RestController
@RequestMapping("/file")
public class FileController {
    FileService fileService;
    FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseData> uploadFile(@RequestParam("location") String location, @RequestParam MultipartFile file) {
        String data = fileService.saveFile(location, file);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.UPLOAD_FILE_SUCCESS)
                .data(data)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    @GetMapping("/download/excel/attendance")
    public ResponseEntity<ResponseCM> downloadExcelAttendance(@RequestParam("startDate") String startDate,
                                                              @RequestParam("endDate") String endDate){
        return ResponseEntity.ok(fileService.downloadExcelAttendance(LocalDate.parse(startDate), LocalDate.parse(endDate)));

    }

    @GetMapping("/download/excel/vacation")
    public ResponseEntity<ResponseCM> downloadExcelVacation(@RequestParam("startDate") String startDate,
                                                              @RequestParam("endDate") String endDate){
        return ResponseEntity.ok(fileService.downloadExcelVacation(LocalDate.parse(startDate), LocalDate.parse(endDate)));

    }
}
