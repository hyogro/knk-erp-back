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

    //파일 업로드
    //request: 저장 경로, 파일
    //response: 저장된 파일명
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

    //출퇴근 엑셀 다운로드
    //request: 시작, 종료일
    //response: 파일
    @GetMapping("/download/excel/attendance")
    public ResponseEntity<ResponseCM> downloadExcelAttendance(@RequestParam("startDate") String startDate,
                                                              @RequestParam("endDate") String endDate){
        return ResponseEntity.ok(fileService.downloadExcelAttendance(LocalDate.parse(startDate), LocalDate.parse(endDate)));

    }

    //휴가 엑셀 다운로드
    //request: 시작, 종료일
    //response: 파일
    @GetMapping("/download/excel/vacation")
    public ResponseEntity<ResponseCM> downloadExcelVacation(@RequestParam("startDate") String startDate,
                                                              @RequestParam("endDate") String endDate){
        return ResponseEntity.ok(fileService.downloadExcelVacation(LocalDate.parse(startDate), LocalDate.parse(endDate)));

    }
}
