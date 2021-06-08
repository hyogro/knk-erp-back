package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 목록에 새로운 부서 추가
    @PostMapping("/createDepartment")
    public ResponseEntity<Create_DepartmentDTO_RES> createDepartment(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.createDepartment(departmentDTOReq));
    }

    // 부서 목록 읽어오기
    @PostMapping("/readDepartment")
    public ResponseEntity<Read_DepartmentDTO_RES> readDepartment(){
        return ResponseEntity.ok(departmentService.readDepartment());
    }

    // 부서 목록 수정
    @PostMapping("/updateDepartment")
    public ResponseEntity<Update_DepartmentDTO_RES> updateDepartment(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.updateDepartment(departmentDTOReq));
    }

    // 부서 목록에서 부서 삭제
    @PostMapping("/deleteDepartment")
    public ResponseEntity<Delete_DepartmentDTO_RES> deleteDepartment(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.deleteDepartment(departmentDTOReq));
    }

    /**2021-06-07 15:07 이상훈 추가
    토큰 받아 해당 직원의 부서 명과 부서인원 리턴**/
    @PostMapping("/readDepartmentNameAndMemberCount")
    public ResponseEntity<RES_DepNameAndMemCount> readDepartmentNameAndMemberCount(@RequestHeader(value = "token") String token){
        return ResponseEntity.ok(departmentService.readDepartmentNameAndMemberCount(token.substring(7)));
    }
}
