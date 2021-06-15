package knk.erp.api.shlee.schedule.responseEntity.schedule;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class REQ_readScheduleListOption {
    private String viewOption;
    private int page;
    private int size;
}
