package knk.erp.api.shlee.file.service;

import knk.erp.api.shlee.account.entity.Member;
import knk.erp.api.shlee.account.entity.MemberRepository;
import knk.erp.api.shlee.file.entity.File;
import knk.erp.api.shlee.file.repository.FileRepository;
import knk.erp.api.shlee.schedule.entity.Attendance;
import knk.erp.api.shlee.schedule.entity.Vacation;
import knk.erp.api.shlee.schedule.repository.AttendanceRepository;
import knk.erp.api.shlee.schedule.repository.VacationRepository;
import knk.erp.api.shlee.schedule.responseEntity.ResponseCM;
import knk.erp.api.shlee.schedule.specification.AS;
import knk.erp.api.shlee.schedule.specification.VS;
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
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final AttendanceRepository attendanceRepository;
    private final MemberRepository memberRepository;
    private final VacationRepository vacationRepository;
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

    private LinkedHashMap<String, List<LocalDate>> makeAttendanceWorkbookSheetTitle(LocalDate startDate, LocalDate endDate){
        int gap = (int)ChronoUnit.DAYS.between(startDate, endDate);
        LocalDate targetDate = startDate;
        List<LocalDate> targetDateList = new ArrayList<>();
        LinkedHashMap<String, List<LocalDate>> sheetMap = new LinkedHashMap<>();

        int tmpMonth = targetDate.getMonthValue();
        String sheetTitle = targetDate.getYear() + "-" + targetDate.getMonthValue();
        for(int i=0; i<= gap; i++) {

            if(targetDate.getMonthValue() != tmpMonth){
                sheetMap.put(sheetTitle, targetDateList);
                sheetTitle = targetDate.getYear() + "-" + targetDate.getMonthValue();
                targetDateList = new ArrayList<>();
                tmpMonth = targetDate.getMonthValue();
            }
            targetDateList.add(targetDate);
            targetDate = targetDate.plusDays(1);
        }
        sheetMap.put(sheetTitle, targetDateList);
        return sheetMap;
    }

    private InputStream makeAttendanceWorkbookFile(LocalDate startDate, LocalDate endDate) throws IOException {

        List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.attendanceDateBetween(startDate, endDate)));

        List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.approve1Is(true)).and(VS.approve2Is(true)));

        List<Member> memberList = memberRepository.findAllByDeletedIsFalse();
        memberList.removeIf(i -> i.getMemberName().equals("관리자"));

        LinkedHashMap<String, List<LocalDate>> sheetMap = makeAttendanceWorkbookSheetTitle(startDate, endDate);
        HashMap<String, String> colAttendanceDataSet = new HashMap<>();
        HashMap<String, String> colVacationDataSet = new HashMap<>();

        for (Attendance attendance : attendanceList){
            String key = attendance.getAuthor().getMemberId() + attendance.getAttendanceDate().toString();
            String val = attendance.getOnWork() + "~" + attendance.getOffWork();

            colAttendanceDataSet.put(key, val);
        }
//        for(Vacation vacation : vacationList){
//            long days = ChronoUnit.DAYS.between(hiredDate, today);
//
//            String key = vacation.getAuthor().getMemberId() + vacation.getAttendanceDate().toString();
//            String val = attendance.getOnWork() + "~" + attendance.getOffWork();
//
//            colVacationDataSet.put(key,val);
//        }

        Workbook wb = new XSSFWorkbook();

        for(String key : sheetMap.keySet()){
            Sheet sheet = wb.createSheet(key+" 출퇴근 정보");
            Row row = null;
            Cell cell = null;
            int rowNum = 0;

            // ↓↓↓↓ Header 세팅↓↓↓↓
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellValue("부서");
            cell = row.createCell(1);
            cell.setCellValue("이름(아이디)");
            int idx = 2;

            // 날짜 세팅
            for(LocalDate date : sheetMap.get(key)){
                cell = row.createCell(idx);
                cell.setCellValue(date.toString());
                idx++;
            }
            // ↑↑↑↑ Header 세팅↑↑↑↑

            row.setHeight((short)512);

            for(Member member : memberList){
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellValue(member.getDepartment().getDepartmentName());
                cell = row.createCell(1);
                cell.setCellValue(member.getMemberName() + "(" + member.getMemberId() + ")");
                int index = 2;
                for(LocalDate date : sheetMap.get(key)){
                    cell = row.createCell(index);
                    String col = (colAttendanceDataSet.get(member.getMemberId()+date.toString()));
                    if(col == null){
                        col = "값 없음";
                    }
                    cell.setCellValue(col);
                    index++;
                }
            }
            for(int i = 0 ; i<idx; i++){
                sheet.autoSizeColumn(i);
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
