package knk.erp.api.shlee.controller;

import knk.erp.api.shlee.model.ApiResponseMessage;
import knk.erp.api.shlee.service.AccountServiceImpl;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/account")
public class AccountController {

    AccountServiceImpl accountService;

    AccountController(AccountServiceImpl accountService){
        this.accountService = accountService;
    }

    @PostMapping("/hello")
    public String hello(){
        return "gateway hello!!??";
    }

    @PostMapping("/")
    public String index(){
        return "this is gateway index!";
    }


    @PostMapping("/callAccount")
    public ResponseEntity<ApiResponseMessage> callAccount(@RequestBody HashMap<String, String> requestData){
        String data = accountService.accountHello();
        ApiResponseMessage responseMessage = new ApiResponseMessage("success", data, "", "");
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody HashMap<String, String> requestData){
        JSONObject jb = new JSONObject(requestData);
        return new ResponseEntity<>(accountService.login(jb), HttpStatus.OK);
    }
}
