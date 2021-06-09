package knk.erp.api.shlee.board.dto.board;

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
    private List<String> reference_name;
    private String content;
    private String boardType;
    private String writer_name;
    private String writer_department;
    private LocalDateTime create_date;
    private LocalDateTime update_date;
}
