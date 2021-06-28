package knk.erp.api.shlee.admin.controller;

import knk.erp.api.shlee.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    @PostMapping("")
    public String makeAdmin(){
        return adminService.makeAdmin();
    }
    @GetMapping("")
    public String makeDefaultUser(){
        return adminService.makeDefaultUser();
    }
}
