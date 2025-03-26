package iot.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/face")
public class FaceRecognitionController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/start")
    public String startFaceRecognition(@RequestBody UserRequest request) {
        String pythonServerUrl = "http://localhost:8000/connect";

        // Forward React's WebRTC offer to Python
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(pythonServerUrl, request, String.class);

        return "Sent WebRTC details to Python";
    }

    @PostMapping("/result")
    public void receiveRecognitionResult(@RequestBody FaceRecognitionResult result) {

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
class FaceRecognitionResult {
    public String userId;
    public String status;
}
