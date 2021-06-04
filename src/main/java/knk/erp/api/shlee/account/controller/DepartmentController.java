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

    @PostMapping("/create")
    public ResponseEntity<Create_DepartmentDTO_RES> create(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.create(departmentDTOReq));
    }

    @PostMapping("/read")
    public ResponseEntity<Read_DepartmentDTO_RES> read(){
        System.out.println("123123");
        return ResponseEntity.ok(departmentService.read());
    }
    /*
    @PostMapping("/update")
    public ResponseEntity<Update_DepartmentDTO_RES> update(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.update(departmentDTOReq));
    }

    @PostMapping("/delete")
    public ResponseEntity<Delete_DepartmentDTO_RES> delete(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.delete(departmentDTOReq));
    }
    */
}
