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
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
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
import java.time.LocalTime;
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

    private void resolveFile(InputStream inputStream, String location, String fileName) throws IOException {
        Path loc = this.path.resolve(location + "/" + fileName);
        Files.copy(inputStream, loc, StandardCopyOption.REPLACE_EXISTING);
    }

    private File saveEntity(String location, String originalFilename) {
        String memberId = getMemberId();
        String extension = getExtension(originalFilename);
        String fileName = makeFileName(extension);

        File file = File.builder().originalFileName(originalFilename).location(location).fileName(fileName).extension(extension).memberId(memberId).build();
        File saveFile = fileRepository.save(file);
        saveFile.setFileName(saveFile.getId() + saveFile.getFileName());
        return fileRepository.save(saveFile);
    }

    public ResponseCM saveFile(String location, MultipartFile multipartFile) {
        try {
            String fileName = saveEntity(location, multipartFile.getOriginalFilename()).getFileName();

            resolveFile(multipartFile.getInputStream(), location, fileName);

            return new ResponseCM("FS001", fileName);
        } catch (Exception e) {
            return new ResponseCM("FS002", e.getMessage());
        }
    }

    private LinkedHashMap<String, List<LocalDate>> makeAttendanceWorkbookSheetTitle(LocalDate startDate, LocalDate endDate) {
        int gap = (int) ChronoUnit.DAYS.between(startDate, endDate);
        LocalDate targetDate = startDate;
        List<LocalDate> targetDateList = new ArrayList<>();
        LinkedHashMap<String, List<LocalDate>> sheetMap = new LinkedHashMap<>();

        int tmpMonth = targetDate.getMonthValue();
        String sheetTitle = targetDate.getYear() + "-" + targetDate.getMonthValue();
        for (int i = 0; i <= gap; i++) {
            if (targetDate.getMonthValue() != tmpMonth) {
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

    private XSSFCellStyle getStyle(XSSFWorkbook wb, String tag) {

        XSSFCellStyle style = wb.createCellStyle();
        switch (tag) {
            case "title":
                style.setAlignment(HorizontalAlignment.CENTER);
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.MEDIUM);
                style.setBorderBottom(BorderStyle.MEDIUM);
                style.setBorderLeft(BorderStyle.MEDIUM);
                style.setBorderRight(BorderStyle.MEDIUM);
                break;
            case "DepMem":
                style.setAlignment(HorizontalAlignment.LEFT);
                style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                break;
            case "dataG":
                style.setAlignment(HorizontalAlignment.LEFT);
                style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                break;
            case "dataR":
                style.setAlignment(HorizontalAlignment.LEFT);
                style.setFillForegroundColor(IndexedColors.CORAL.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                break;
            case "dataB":
                style.setAlignment(HorizontalAlignment.LEFT);
                style.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
                style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                style.setBorderTop(BorderStyle.THIN);
                style.setBorderBottom(BorderStyle.THIN);
                style.setBorderLeft(BorderStyle.THIN);
                style.setBorderRight(BorderStyle.THIN);
                break;
        }
        return style;
    }

    private InputStream makeAttendanceWorkbookFile(LocalDate startDate, LocalDate endDate) throws IOException {

        List<Attendance> attendanceList = attendanceRepository.findAll(AS.delFalse().and(AS.attendanceDateBetween(startDate, endDate)));

        List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.approve1Is(true)).and(VS.approve2Is(true)));

        List<Member> memberList = memberRepository.findAllByDeletedIsFalse();
        memberList.removeIf(i -> i.getMemberName().equals("관리자"));

        LinkedHashMap<String, List<LocalDate>> sheetMap = makeAttendanceWorkbookSheetTitle(startDate, endDate);
        HashMap<String, String> colAttendanceDataSet = new HashMap<>();
        HashMap<String, String> colVacationDataSet = new HashMap<>();

        for (Attendance attendance : attendanceList) {
            String key = attendance.getAuthor().getMemberId() + attendance.getAttendanceDate().toString();
            String val = attendance.getOnWork() + "~" + attendance.getOffWork();

            colAttendanceDataSet.put(key, val);
        }

        for (Vacation vacation : vacationList) {
            if (vacation.getType().equals("연차")) {
                int days = (int) ChronoUnit.DAYS.between(vacation.getStartDate(), vacation.getEndDate());
                LocalDate targetDate = vacation.getStartDate().toLocalDate();
                for (int i = 0; i <= days; i++) {
                    String key = vacation.getAuthor().getMemberId() + targetDate.toString();
                    String val = vacation.getType();
                    colVacationDataSet.put(key, val);

                    targetDate = targetDate.plusDays(1);
                }
            } else {
                String val;
                if (vacation.getType().equals("시간제")) {
                    val = vacation.getStartDate().toLocalTime() + "~" + vacation.getEndDate().toLocalTime();
                } else {
                    val = vacation.getType();
                }
                String key = vacation.getAuthor().getMemberId() + vacation.getStartDate().toLocalDate();
                colVacationDataSet.put(key, val);
            }
        }

        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFCellStyle titleStyle = getStyle(wb, "title");
        XSSFCellStyle dnStyle = getStyle(wb, "DepMem");
        XSSFCellStyle dataGStyle = getStyle(wb, "dataG");
        XSSFCellStyle dataRStyle = getStyle(wb, "dataR");
        XSSFCellStyle dataBStyle = getStyle(wb, "dataB");

        for (String key : sheetMap.keySet()) {
            XSSFSheet sheet = wb.createSheet(key + " 출퇴근 정보");
            Row row;
            Cell cell;
            int rowNum = 0;

            // ↓↓↓↓ Header 세팅↓↓↓↓
            row = sheet.createRow(rowNum++);
            cell = row.createCell(0);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("부서");
            cell = row.createCell(1);
            cell.setCellStyle(titleStyle);
            cell.setCellValue("이름(아이디)");
            int idx = 2;

            // 날짜 세팅
            for (LocalDate date : sheetMap.get(key)) {
                cell = row.createCell(idx);
                cell.setCellStyle(titleStyle);
                cell.setCellValue(date.toString());
                idx++;
            }
            // ↑↑↑↑ Header 세팅↑↑↑↑

            for (Member member : memberList) {
                row = sheet.createRow(rowNum++);
                cell = row.createCell(0);
                cell.setCellStyle(dnStyle);
                cell.setCellValue(member.getDepartment().getDepartmentName());
                cell = row.createCell(1);
                cell.setCellStyle(dnStyle);
                cell.setCellValue(member.getMemberName() + "(" + member.getMemberId() + ")");
                int index = 2;
                for (LocalDate date : sheetMap.get(key)) {
                    cell = row.createCell(index);
                    String att = colAttendanceDataSet.get(member.getMemberId() + date.toString());
                    String vac = colVacationDataSet.get(member.getMemberId() + date);
                    String col = "";
                    if (att != null && vac == null) {
                        cell.setCellStyle(dataBStyle);
                        col += "○(" + att + ")"; //출근기록 있으면서 휴가기록 없는것
                    }
                    if (att != null && vac != null) {
                        cell.setCellStyle(dataGStyle);
                        col += "○(" + att + ")(휴가: " + vac + ")"; //출근, 휴가기록 둘다 있는것
                    }
                    if (att == null && vac != null) {
                        cell.setCellStyle(dataGStyle);
                        col += "□(" + vac + ")";   //출근기록 없으면서 휴가기록 있는것
                    }
                    if (att == null && vac == null) {
                        cell.setCellStyle(dataRStyle);
                        col += "Ⅹ";
                    }
                    col = col.replace("null", "기록 없음");
                    col = col.replace("T", " ");
                    cell.setCellValue(col);
                    index++;
                }
            }
            for (int i = 0; i < idx; i++) {
                sheet.autoSizeColumn(i);
            }
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        byte[] bArray = bos.toByteArray();
        return new ByteArrayInputStream(bArray);
    }

    public ResponseCM downloadExcelAttendance(LocalDate startDate, LocalDate endDate) {
        try {
            InputStream is = makeAttendanceWorkbookFile(startDate, endDate);
            String location = "excel";

            String fileName = saveEntity(location, startDate + "~" + endDate + "직원 출퇴근 장부.xlsx").getFileName();
            resolveFile(is, location, fileName);
            return new ResponseCM("ES001", fileName);
        } catch (Exception e) {
            return new ResponseCM("ES002", e.getMessage());
        }
    }

    private InputStream makeVacationWorkbookFile(LocalDate startDate, LocalDate endDate) throws IOException {
        LocalDateTime sd = LocalDateTime.of(startDate, LocalTime.MIN);
        LocalDateTime ed = LocalDateTime.of(endDate, LocalTime.MAX);

        List<Vacation> vacationList = vacationRepository.findAll(VS.delFalse().and(VS.approve1Is(true)).and(VS.approve2Is(true)).and(VS.vacationDateBetween(sd, ed)));


        XSSFWorkbook wb = new XSSFWorkbook();

        XSSFCellStyle titleStyle = getStyle(wb, "title");
        XSSFCellStyle dnStyle = getStyle(wb, "DepMem");
        XSSFCellStyle dataGStyle = getStyle(wb, "dataG");

        XSSFSheet sheet = wb.createSheet(startDate + "~" + "endDate" + " 휴가 정보");

        Row row;
        Cell cell;
        int rowNum = 0;
        // ↓↓↓↓ Header 세팅↓↓↓↓
        row = sheet.createRow(rowNum++);
        cell = row.createCell(0);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("부서");

        cell = row.createCell(1);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("이름(아이디)");

        cell = row.createCell(2);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("타입");

        cell = row.createCell(3);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("시작일");

        cell = row.createCell(4);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("종료일");

        cell = row.createCell(5);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("사유");

        cell = row.createCell(6);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("1차 승인자");

        cell = row.createCell(7);
        cell.setCellStyle(titleStyle);
        cell.setCellValue("2차 승인자");

        // ↑↑↑↑ Header 세팅↑↑↑↑

        for (Vacation vacation : vacationList){
            Member member = vacation.getAuthor();
            row = sheet.createRow(rowNum++);

            cell = row.createCell(0);
            cell.setCellStyle(dnStyle);
            cell.setCellValue(member.getDepartment().getDepartmentName());

            cell = row.createCell(1);
            cell.setCellStyle(dnStyle);
            cell.setCellValue(member.getMemberName() + "(" + member.getMemberId() + ")");

            cell = row.createCell(2);
            cell.setCellStyle(dnStyle);
            cell.setCellValue(vacation.getType());

            cell = row.createCell(3);
            cell.setCellStyle(dataGStyle);
            cell.setCellValue(dateToKorean(vacation.getStartDate()));

            cell = row.createCell(4);
            cell.setCellStyle(dataGStyle);
            cell.setCellValue(dateToKorean(vacation.getEndDate()));

            cell = row.createCell(5);
            cell.setCellStyle(dataGStyle);
            cell.setCellValue(vacation.getMemo());

            cell = row.createCell(6);
            cell.setCellStyle(dataGStyle);
            cell.setCellValue(vacation.getApprover1().getMemberName());

            cell = row.createCell(7);
            cell.setCellStyle(dataGStyle);
            cell.setCellValue(vacation.getApprover2().getMemberName());


        }

        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        wb.write(bos);
        byte[] bArray = bos.toByteArray();
        return new ByteArrayInputStream(bArray);
    }

    private String dateToKorean(LocalDateTime td){
        return td.getYear()+"년 "+ td.getMonthValue() + "월 " + td.getDayOfMonth() + "일 " + td.getHour() + "시 " + td.getMinute() + "분 ";
    }

    public ResponseCM downloadExcelVacation(LocalDate startDate, LocalDate endDate) {
        try {
            InputStream is = makeVacationWorkbookFile(startDate, endDate);
            String location = "excel";

            String fileName = saveEntity(location, startDate + "~" + endDate + "직원 휴가 장부.xlsx").getFileName();
            resolveFile(is, location, fileName);
            return new ResponseCM("ES001", fileName);
        } catch (Exception e) {
            return new ResponseCM("ES002", e.getMessage());
        }
    }

}
