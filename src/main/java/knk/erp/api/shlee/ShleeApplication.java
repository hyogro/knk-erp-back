package knk.erp.api.shlee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.TimeZone;

@SpringBootApplication
public class ShleeApplication {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @PostConstruct
    void started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        logger.info("서비스 시작");
        logger.info("현재시각: {}", LocalDateTime.now());
    }

    public static void main(String[] args) {
        SpringApplication.run(ShleeApplication.class, args);
    }

}
