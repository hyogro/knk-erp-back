package knk.erp.api.shlee.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseCM implements ResponseData {
    private ResponseCode message;
    private String code;

    @Builder
    public ResponseCM(ResponseCode responseCode){
        this.message = responseCode;
        this.code = responseCode.getCode();
    }

}
