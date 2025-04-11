package iot.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import iot.service.AITaskService;
import iot.service.FaceHandlingService;
import iot.service.impl.AITaskServiceImpl;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api/face")
public class FaceRecognitionController {

    private final AITaskServiceImpl AITaskServiceImpl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private FaceHandlingService faceHandlingService;

    @Autowired
    private AITaskService aiTaskService;

    FaceRecognitionController(AITaskServiceImpl AITaskServiceImpl) {
        this.AITaskServiceImpl = AITaskServiceImpl;
    }

    @PostMapping("/identify")
    public ResponseEntity<ApiResponse<Object>> startFaceRecognition() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from the security context

        // Send username + embedding to FASTAPI through api
        if (aiTaskService.sendEmbeddingForIdentification(username)) {
            return ResponseEntity.ok()
                    .body(ApiResponse.<Object>builder().error("Success to confirm face recognition").build());
        } else {
            return ResponseEntity.status(401)
                    .body(ApiResponse.<Object>builder().message("Fail to confirm face recognition").build());
        }

    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> registerFaceRecognition() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from the security context
        if (aiTaskService.sendUsernameForRegistration(username)) {
            return ResponseEntity.ok()
                    .body(ApiResponse.<Object>builder().error("Success to confirm face registration").build());
        } else {
            return ResponseEntity.status(401)
                    .body(ApiResponse.<Object>builder().message("Fail to confirm face registration").build());
        }
    }

    // Get embedding from FASTAPI through api
    // /face/get_embedding?username={username}
    @PostMapping("/server/send_embedding")
    public ResponseEntity<ApiResponse<Object>> getEmbedding(
            @RequestBody ApiResponse<GetEmbeddingResponse> requestBody) {
        List<Double> embedding = requestBody.getData().getEmbedding();
        String username = requestBody.getData().getUsername();

        // Store embedding to database
        if (faceHandlingService.addEmbeddingByUsername(username, embedding)) {
            return ResponseEntity.ok()
                    .body(ApiResponse.<Object>builder().message("Success to get embedding").build());
        } else {
            return ResponseEntity.status(401)
                    .body(ApiResponse.<Object>builder().message("Fail to get embedding").build());
        }
    }

    // ########## TEST API ##########
    @GetMapping("/test/get_embedding")
    public ResponseEntity<ApiResponse<Object>> testGetEmbedding() {
        if (aiTaskService.sendUsernameForRegistration("user1")) {
            return ResponseEntity.ok()
                    .body(ApiResponse.<Object>builder().message("Success to get embedding").build());
        } else {
            return ResponseEntity.status(401)
                    .body(ApiResponse.<Object>builder().message("Fail to get embedding").build());
        }
    }

}

@Builder
@Getter
@Setter
class GetEmbeddingResponse {
    private String username;
    private List<Double> embedding;
    // Getters and setters
}