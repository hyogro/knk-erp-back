package knk.erp.api.shlee.schedule.responseEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseCM {
    private String code;
    private String message;

    public ResponseCM(String code, String message){
        this.code = code;
        this.message = message;
    }

    public ResponseCM(String code){
        this.code = code;
        this.message = "";
    }
}
