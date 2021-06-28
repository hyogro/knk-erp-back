package knk.erp.api.shlee.file.service;

import knk.erp.api.shlee.file.entity.File;
import knk.erp.api.shlee.file.repository.FileRepository;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.repository.AttendanceRepository;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.schedule.specification.AS;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final AttendanceRepository attendanceRepository;
    private final Path path = Paths.get("/home/ubuntu/files");

    private String getMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    private String makeFileName(String extension) {
        String fileName = LocalDateTime.now() + "";
        return fileName.replace("T", "").replace("-", "").replace(".", "").replace(":", "") + extension;
    }

    private String getExtension(String originalFilename) {
        return originalFilename.substring(originalFilename.lastIndexOf("."));
    }

    private void resolveFile(InputStream inputStream, String fileName) throws IOException {
        Path location = this.path.resolve(fileName);
        Files.copy(inputStream, location, StandardCopyOption.REPLACE_EXISTING);
    }

    private File saveEntity(String originalFilename) {
        String memberId = getMemberId();
        String extension = getExtension(originalFilename);
        String fileName = makeFileName(extension);

        File file = File.builder().originalFileName(originalFilename).fileName(fileName).extension(extension).memberId(memberId).build();
        return fileRepository.save(file);
    }

    public ResponseCM saveFile(MultipartFile multipartFile) {
        try {
            String fileName = saveEntity(multipartFile.getOriginalFilename()).getFileName();

            resolveFile(multipartFile.getInputStream(), fileName);

            return new ResponseCM("FS001", fileName);
        } catch (Exception e) {
            return new ResponseCM("FS002", e.getMessage());
        }
    }

    private InputStream makeAttendanceWorkbookFile(LocalDate startDate, LocalDate endDate) throws IOException {

        List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.attendanceDateBetween(startDate, endDate)));
        int startMonth = startDate.getMonthValue();
        int endMonth = endDate.getMonthValue();
        Workbook wb = new XSSFWorkbook();

        for(int m = startMonth; m <= endMonth; m++){
            Sheet sheet = wb.createSheet(m+"월 출퇴근 정보");
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            // Header
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("번호");
            cell = row.createCell(1);
            cell.setCellValue("이름");
            cell = row.createCell(2);
            cell.setCellValue("제목");

            // Body
            for (int i=0; i<3; i++) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(i);
                cell = row.createCell(1);
                cell.setCellValue(i+"_name");
                cell = row.createCell(2);
                cell.setCellValue(i+"_title");
            }
        }



        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        byte[] bArray = bos.toByteArray();
        return new ByteArrayInputStream(bArray);
    }

    public ResponseCM downloadExcelAttendance(LocalDate startDate, LocalDate endDate){
        try {
            InputStream is = makeAttendanceWorkbookFile(startDate, endDate);

            String fileName = saveEntity(startDate + "~" + endDate + "직원 출퇴근 장부.xlsx").getFileName();
            resolveFile(is, fileName);
            return new ResponseCM("ES001", fileName);
        }catch (Exception e){
            return new ResponseCM("ES002", e.getMessage());
        }
    }

}
