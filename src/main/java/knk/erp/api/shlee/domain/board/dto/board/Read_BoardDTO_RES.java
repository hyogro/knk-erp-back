package knk.erp.api.shlee.domain.board.dto.board;

import lombok.*;

import java.util.LinkedHashMap;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Read_BoardDTO_RES {
    private Read_BoardDTO readBoardDTO;
    private List<Read_ReferenceMemberDTO> readReferenceMemberDTO;

    public Read_BoardDTO_RES(Read_BoardDTO readBoardDTO, List<Read_ReferenceMemberDTO> readReferenceMemberDTO) {
        this.readBoardDTO = readBoardDTO;
        this.readReferenceMemberDTO = readReferenceMemberDTO;
    }
}
