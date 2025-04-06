package iot.controllers;

import org.springframework.web.bind.annotation.RestController;

import iot.config.JwtUtils;
import iot.model.User;
import iot.service.PersonalInfoService;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

@Builder
class loginResponse {
    private String token;
    private String message;
    private String error;
}

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private PersonalInfoService userService;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<loginResponse> login(@RequestBody loginDTO loginDTO) {
        System.out.println(loginDTO.getUsername());
        String token = userService.verifyAccount(loginDTO.getUsername(), loginDTO.getPassword());
        if (token == null) {
            return ResponseEntity.status(401)
                    .body(loginResponse.builder().message("Login failed").error("Invalid username or password")
                            .build());
        } else {
            return ResponseEntity.ok()
                    .body(loginResponse.builder().message("Login successfully").token(token).build());
        }
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
