package iot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import iot.model.Home;
import iot.service.PersonalInfoService;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @Autowired
    private PersonalInfoService personalInfoService;

    @PostMapping("/add")
    public String addHome(@RequestBody HomeDTO homeDTO) {
        Home home = Home.builder().address(homeDTO.getAddress())
                .houseType(homeDTO.getHomeType())
                .build();
        personalInfoService.createHome(home);
        return "Home added successfully";
    }

    @GetMapping("/test")
    public String getFirstHome() {
        Home home = personalInfoService.getHomeById(1L);
        return "Home ID: " + home.getHomeId() + ", Address: " + home.getAddress() + ", Type: " + home.getHouseType();
    }
}

@Getter
@Setter
class HomeDTO {
    private String address;
    private String homeType;
}