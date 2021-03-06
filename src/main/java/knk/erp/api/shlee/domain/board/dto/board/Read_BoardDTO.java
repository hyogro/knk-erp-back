package knk.erp.api.shlee.domain.board.dto.board;

import knk.erp.api.shlee.domain.file.entity.File;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Read_BoardDTO {
    private String title;
    private String content;
    private String boardType;
    private String writerMemberName;
    private String writerMemberId;
    private String writer_department;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
    private List<File> file;
    private int count;
    private List<String> visitors;
}
