package knk.erp.api.shlee.domain.Fixtures.dto;

import lombok.*;
import org.springframework.data.domain.Page;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ReadAll_FixturesFormDTO_RES {
    private Page<ReadAll_FixturesFormDTO> page;
    private int pageSize;
}
