package knk.erp.api.shlee.file.controller;

import knk.erp.api.shlee.file.service.FileService;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@RestController
@RequestMapping("/file")
public class FileController {
    FileService fileService;
    FileController(FileService fileService){
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseCM> uploadFile(@RequestParam("location") String location, @RequestParam MultipartFile file) {
        if(file.isEmpty()) {
            ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(fileService.saveFile(location, file));
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
