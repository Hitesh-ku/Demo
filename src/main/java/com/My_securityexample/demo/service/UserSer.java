package com.My_securityexample.demo.service;

import com.My_securityexample.demo.dto.APIResponseDto;
import com.My_securityexample.demo.dto.UserDto;
import com.My_securityexample.demo.entity.User;
import com.My_securityexample.demo.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserSer {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder en;
    public APIResponseDto<String> register(UserDto dto){
        APIResponseDto<String> response=new APIResponseDto<>();
        if (userRepository.existsByUsername(dto.getUsername())){
            response.setMessage("Registration Failed!");
            response.setStatus(403);
            response.setData("User with this username is already exist");
            return response;
        }
        if (userRepository.existsByEmail(dto.getEmail())){
            response.setMessage("Registration Failed!");
            response.setStatus(403);
            response.setData("User with this email id is already exist.");
            return response;
        }
        User user=new User();
        BeanUtils.copyProperties(dto,user);
        user.setPassword(en.encode(dto.getPassword()));
        user.setRole("ROLE_USER");

        userRepository.save(user);
        response.setMessage("Registration Success!");
        response.setStatus(200);
        response.setData("Registration Successful!");
        return response;
    }
}
