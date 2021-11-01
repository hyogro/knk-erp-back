package knk.erp.api.shlee.domain.account.controller;

import knk.erp.api.shlee.common.dto.ResponseCM;
import knk.erp.api.shlee.common.dto.ResponseCMD;
import knk.erp.api.shlee.common.dto.ResponseCode;
import knk.erp.api.shlee.common.dto.ResponseData;
import knk.erp.api.shlee.domain.account.dto.department.*;
import knk.erp.api.shlee.domain.account.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/department")
public class DepartmentController {

    private final DepartmentService departmentService;

    // 부서 목록에 새로운 부서 추가
    @PostMapping("")
    public ResponseEntity<ResponseData> createDepartment(@RequestBody DepartmentDTO_REQ departmentDTOReq){
        departmentService.createDepartment(departmentDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.CREATE_DEPARTMENT_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 부서 목록 읽어오기
    @GetMapping("")
    public ResponseEntity<ResponseData> readDepartment(){
        List<Read_DepartmentDTO> departmentList = departmentService.readDepartment();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_DEPARTMENT_SUCCESS)
                .data(departmentList)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 부서 상세 보기
    @GetMapping("/{dep_id}")
    public ResponseEntity<ResponseData> readDetailDepartment(@PathVariable Long dep_id){
        ReadDetail_DepartmentDTO_RES readDetails = departmentService.readDetailDepartment(dep_id);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_DETAIL_DEPARTMENT_SUCCESS)
                .data(readDetails)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 해당 부서의 부서원을 제외한 모든 직원 리스트
    @GetMapping("/readNotThisDepartmentMember/{dep_id}")
    public ResponseEntity<ResponseData> readNotThisDepartmentMember(@PathVariable Long dep_id){
        List<Read_DepartmentMemberListDTO> otherMemberList = departmentService.readNotThisDepartmentMember(dep_id);

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_OTHER_DEPARTMENT_MEMBER_SUCCESS)
                .data(otherMemberList)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 부서 이름 수정
    @PutMapping("/{dep_id}")
    public ResponseEntity<ResponseData> updateDepartment(@PathVariable Long dep_id, @RequestBody DepartmentDTO_REQ departmentDTOReq){
        departmentService.updateDepartment(dep_id, departmentDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_DEPARTMENT_NAME_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 부서 목록에서 부서 삭제
    @DeleteMapping("/{dep_id}")
    public ResponseEntity<ResponseData> deleteDepartment(@PathVariable Long dep_id){
        departmentService.deleteDepartment(dep_id);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_DEPARTMENT_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 부서 팀장 수정
    @PutMapping("/updateLeader/{dep_id}")
    public ResponseEntity<ResponseData> updateLeader(@PathVariable Long dep_id,
                                                                       @RequestBody Update_DepartmentLeaderDTO_REQ updateDepartmentLeaderDTOReq){
        departmentService.updateLeader(dep_id, updateDepartmentLeaderDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.UPDATE_DEPARTMENT_LEADER_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 부서 멤버 추가
    @PutMapping("/addMember/{dep_id}")
    public ResponseEntity<ResponseData> addMemberToDepartment(@PathVariable Long dep_id,
                                                                             @RequestBody DepartmentMemberDTO_REQ departmentMemberDTOReq){
        departmentService.addMemberToDepartment(dep_id, departmentMemberDTOReq);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.ADD_DEPARTMENT_MEMBER_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    // 부서 멤버 삭제
    @DeleteMapping("/deleteMember/{memberId}")
    public ResponseEntity<ResponseData> deleteMemberToDepartment(@PathVariable String memberId){
        departmentService.deleteMemberToDepartment(memberId);

        ResponseCM responseCM = ResponseCM
                .builder()
                .responseCode(ResponseCode.DELETE_DEPARTMENT_MEMBER_SUCCESS)
                .build();

        return new ResponseEntity<>(responseCM, HttpStatus.OK);
    }

    //토큰 받아 해당 직원의 부서 명과 부서인원 리턴
    @GetMapping("/readDepartmentNameAndMemberCount")
    public ResponseEntity<ResponseData> readDepartmentNameAndMemberCount(){
        DepartmentNameAndMemberCountDTO departmentNameAndMemberCountDTO = departmentService.readDepartmentNameAndMemberCount();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_DEPARTMENT_NAME_AND_MEMBER_COUNT_SUCCESS)
                .data(departmentNameAndMemberCountDTO)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }

    // 조직도 읽기
    @GetMapping("/readOrganizationChart")
    public ResponseEntity<ResponseData> readOrganizationChart(){
        List<OrganizationChartDTO> chartList = departmentService.readOrganizationChart();

        ResponseCMD responseCMD = ResponseCMD
                .builder()
                .responseCode(ResponseCode.READ_ORGANIZATION_CHART_SUCCESS)
                .data(chartList)
                .build();

        return new ResponseEntity<>(responseCMD, HttpStatus.OK);
    }
}
