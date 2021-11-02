package knk.erp.api.shlee.domain.Materials.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.Materials.dto.MaterialsDTO;
import knk.erp.api.shlee.domain.Materials.service.MaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialsController {
    private final MaterialsService materialsService;

    //장기자재현황 사진 파일 이름 리스트 저장
    @PostMapping("")
    public ResponseEntity<ResponseData> createMaterials(@RequestBody MaterialsDTO materialsDTO){
        materialsService.createMaterials(materialsDTO);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_MATERIALS_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    //장기자재현황 사진 파일 이름 리스트 Get
    @GetMapping("")
    public ResponseEntity<ResponseData> readMaterials(){
        List<String> materials = materialsService.readMaterials();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_MATERIALS_SUCCESS)
                .data(materials)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
