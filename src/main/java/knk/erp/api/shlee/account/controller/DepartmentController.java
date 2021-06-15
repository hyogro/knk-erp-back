package knk.erp.api.shlee.account.controller;

import knk.erp.api.shlee.account.dto.department.*;
import knk.erp.api.shlee.account.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 목록에 새로운 부서 추가
    @PostMapping("")
    public ResponseEntity<Create_DepartmentDTO_RES> createDepartment(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.createDepartment(departmentDTOReq));
    }

    // 부서 목록 읽어오기
    @GetMapping("")
    public ResponseEntity<Read_DepartmentDTO_RES> readDepartment(){
        return ResponseEntity.ok(departmentService.readDepartment());
    }

    // 부서 목록 수정
    @PutMapping("/{dep_id}")
    public ResponseEntity<Update_DepartmentDTO_RES> updateDepartment(@PathVariable Long dep_id, @RequestBody DepartmentDTO_REQ departmentDTOReq){
        return ResponseEntity.ok(departmentService.updateDepartment(dep_id, departmentDTOReq));
    }

    // 부서 목록에서 부서 삭제
    @DeleteMapping("/{dep_id}")
    public ResponseEntity<Delete_DepartmentDTO_RES> deleteDepartment(@PathVariable Long dep_id){
        return ResponseEntity.ok(departmentService.deleteDepartment(dep_id));
    }

    // 부서 팀장 수정
    @PutMapping("/updateLeader/{dep_id}")
    public ResponseEntity<UpdateLeader_DepartmentDTO_RES> updateLeader(@PathVariable Long dep_id,
                                                                       @RequestBody Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq){
        return ResponseEntity.ok(departmentService.updateLeader(dep_id, updateDepartmentLeaderDTOReq));
    }

    // 부서 멤버 추가
    @PutMapping("/addMember/{dep_id}")
    public ResponseEntity<Add_DepartmentMemberDTO_RES> addMemberToDepartment(@PathVariable Long dep_id,
                                                                             @RequestBody DepartmentMemberDTO_REQ departmentMemberDTOReq){
        return ResponseEntity.ok(departmentService.addMemberToDepartment(dep_id, departmentMemberDTOReq));
    }

    // 부서 멤버 삭제
    @DeleteMapping("/deleteMember/{dep_id}")
    public ResponseEntity<Delete_DepartmentMemberDTO_RES> deleteMemberToDepartment(@PathVariable Long dep_id,
                                                                                   @RequestParam("memberId") String memberId){
        return ResponseEntity.ok(departmentService.deleteMemberToDepartment(dep_id, memberId));
    }

    /**2021-06-07 15:07 이상훈 추가
    토큰 받아 해당 직원의 부서 명과 부서인원 리턴**/
    @GetMapping("/readDepartmentNameAndMemberCount")
    public ResponseEntity<RES_DepNameAndMemCount> readDepartmentNameAndMemberCount(){
        return ResponseEntity.ok(departmentService.readDepartmentNameAndMemberCount());
    }
}
