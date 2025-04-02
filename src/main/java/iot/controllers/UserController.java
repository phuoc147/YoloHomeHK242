package iot.controllers;

import org.springframework.web.bind.annotation.RestController;

import iot.middleware.JwtUtils;
import iot.model.User;
import iot.service.PersonalInfoService;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Getter
@Setter
class loginDTO {
    private String password;
    private String username;
}

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PersonalInfoService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public String login(@RequestBody loginDTO loginDTO) {
        System.out.println(loginDTO.getUsername());
        return userService.verifyAccount(loginDTO.getUsername(), loginDTO.getPassword());
    }

    // Test api
    @GetMapping("/test")
    public String test() throws InterruptedException {
        Thread.sleep(5000);
        return "Test";
    }

    @GetMapping("/test/getUser")
    public String testGetUser() {
        return userService.getUserById(1L).getUpdatedDate().toString();
    }

}
