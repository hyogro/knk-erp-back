package knk.erp.api.shlee.domain.Materials.controller;

import knk.erp.api.shlee.domain.Materials.dto.Create_MaterialsDTO_RES;
import knk.erp.api.shlee.domain.Materials.dto.MaterialsDTO;
import knk.erp.api.shlee.domain.Materials.dto.Read_MaterialsDTO_RES;
import knk.erp.api.shlee.domain.Materials.service.MaterialsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/materials")
public class MaterialsController {
    private final MaterialsService materialsService;

    //장기자재현황 사진 파일 이름 리스트 저장
    @PostMapping("")
    public ResponseEntity<Create_MaterialsDTO_RES> createMaterials(@RequestBody MaterialsDTO materialsDTO){
        return ResponseEntity.ok(materialsService.createMaterials(materialsDTO));
    }

    //장기자재현황 사진 파일 이름 리스트 Get
    @GetMapping("")
    public ResponseEntity<Read_MaterialsDTO_RES> readMaterials(){
        return ResponseEntity.ok(materialsService.readMaterials());
    }
}
