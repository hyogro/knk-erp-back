package knk.erp.api.shlee;

import knk.erp.api.shlee.schedule.service.AttendanceService;
import knk.erp.api.shlee.schedule.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShleeApplicationTests {
    @Autowired
    AttendanceService attendanceService;
    @Autowired
    ScheduleService scheduleService;

    @Test
    void contextLoads() {
    }

    @Test
    public void justTest(){
        System.out.println("그냥 테스트~~");
    }

}
