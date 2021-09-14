package knk.erp.api.shlee.domain.schedule.responseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ResponseCMDL {
    private String code;
    private String message;
    private List<Object> data;

    public ResponseCMDL(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseCMDL(String code, List<Object> data){
        this.code = code;
        this.data = data;
        this.message = "";
    }
}
