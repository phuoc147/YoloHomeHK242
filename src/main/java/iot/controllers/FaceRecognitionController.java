package iot.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api")
public class FaceRecognitionController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/face_recognition")
    public String startFaceRecognition(@RequestBody UserRequest request) {
        String pythonServerUrl = "http://localhost:8000/connect";

        // Get ip of user
        FaceRecognitionDTO faceRecognitionDTO = new FaceRecognitionDTO();
        faceRecognitionDTO.setUser_ip(request.getWsUrl().split(":")[1].substring(2));

        // Send user ip to Python server
        restTemplate.postForObject(pythonServerUrl, faceRecognitionDTO, String.class);
        return "Face recognition started";
    }

}

@Getter
@Setter
class UserRequest {
    public String userId;
    public String wsUrl;
}

@Getter
@Setter
class FaceRecognitionDTO {
    public String user_ip;
}
