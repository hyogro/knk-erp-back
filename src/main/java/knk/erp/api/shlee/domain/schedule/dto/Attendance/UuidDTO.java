package knk.erp.api.shlee.domain.schedule.dto.Attendance;

import lombok.*;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class UuidDTO {
    private String uuid;
    private String dummy;


    @Builder
    public UuidDTO(String uuid, String dummy) {
        this.uuid = uuid;
        this.dummy = dummy;
    }
}
