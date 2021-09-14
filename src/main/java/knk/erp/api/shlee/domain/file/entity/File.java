package knk.erp.api.shlee.domain.file.entity;

import knk.erp.api.shlee.domain.schedule.entity.Time;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class File extends Time {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String originalFileName;

    @Column
    private String location;

    @Column
    private String fileName;

    @Column
    private String memberId;

    @Column
    private String extension;

    @Builder
    public File(String originalFileName, String location, String fileName, String memberId, String extension) {
        this.originalFileName = originalFileName;
        this.location = location;
        this.fileName = fileName;
        this.memberId = memberId;
        this.extension = extension;
    }
}
