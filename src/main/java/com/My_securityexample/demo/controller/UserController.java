package com.My_securityexample.demo.controller;

import com.My_securityexample.demo.dto.APIResponseDto;
import com.My_securityexample.demo.dto.LoginDto;
import com.My_securityexample.demo.dto.UserDto;
import com.My_securityexample.demo.service.UserSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserSer userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    //http://localhost:8080/api/v1/user/welcome
    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome Back";
    }
    //http://localhost:8080/api/v1/user/register
    @PostMapping("/register")
    public ResponseEntity<APIResponseDto<String>> createUser(
            @RequestBody UserDto dto
            ){
        APIResponseDto<String> response = userService.register(dto);

        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getStatus()));
    }
    @PostMapping("/login")
    public ResponseEntity<APIResponseDto<String>> loigin(
            @RequestBody LoginDto dto
            ){
        UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(dto.getUsername(),dto.getPassword());
        APIResponseDto<String> response=new APIResponseDto<>();
        Authentication authenticate = authenticationManager.authenticate(token);
        if (authenticate.isAuthenticated()){

            response.setMessage("Logged In!");
            response.setStatus(200);
            response.setData("Login Success!");
            return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
        }
        response.setMessage("LogIn Failed!");
        response.setStatus(403);
        response.setData("Unauthorized access!!");
        return new ResponseEntity<>(response,HttpStatusCode.valueOf(response.getStatus()));
    }


}
