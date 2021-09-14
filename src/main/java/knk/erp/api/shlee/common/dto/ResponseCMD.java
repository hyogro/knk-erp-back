package knk.erp.api.shlee.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ResponseCMD implements ResponseData {
    private Object data;
    private ResponseCode message;
    private String code;

    @Builder
    public ResponseCMD(ResponseCode responseCode, Object data){
        this.message = responseCode;
        this.code = responseCode.getCode();
        this.data = data;
    }
}
