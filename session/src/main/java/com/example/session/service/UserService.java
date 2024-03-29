package com.example.session.service;

import com.example.session.db.UserRepository;
import com.example.session.model.LoginRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void login(LoginRequest loginRequest, HttpSession httpSession){
        var id=loginRequest.getId();
        var pw=loginRequest.getPassword();

        var optionalUser=userRepository.findByName(id);
        if(optionalUser.isPresent()){
            var userDto=optionalUser.get();
            if(userDto.getPassword().equals(pw)){
                //세선에 정보저장
                httpSession.setAttribute("USER",userDto);//USER에 userDto 정보 저장 * 세션 인증을 통해 반드시 필요함
            }else{
                throw new RuntimeException("Password Not Match");
            }
        }else{
            throw new RuntimeException("User Not Found");
        }
    }
}
