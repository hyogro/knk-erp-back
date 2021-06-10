package knk.erp.api.shlee.file.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedHashMap;

@Getter
@Setter
@ToString
public class RES_fileSave {
    private String code;
    private String message;
    private LinkedHashMap<String, String> help_codeList = new LinkedHashMap<>();

    private void createCodeList(){
        this.help_codeList.put("SF001", "파일저장 성공");
        this.help_codeList.put("SF002", "파일저장 실패");
    }

    //error
    public RES_fileSave(String code, String message) {
        createCodeList();
        this.code = code;
        this.message = message;
    }

    //ok
    public RES_fileSave(String code) {
        createCodeList();
        this.code = code;
    }
}
