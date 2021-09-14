package knk.erp.api.shlee.domain.schedule.responseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseCMD {
    private String code;
    private String message;
    private Object data;

    public ResponseCMD(String code){
        this.code = code;
        this.message = "";
    }

    public ResponseCMD(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseCMD(String code, Object data){
        this.code = code;
        this.data = data;
        this.message = "";
    }
}
