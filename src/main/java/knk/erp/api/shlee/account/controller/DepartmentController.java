package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println("123123");
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
}
