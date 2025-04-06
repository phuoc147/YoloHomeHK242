package iot.controllers;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@RestController
@RequestMapping("/api")
public class FaceRecognitionController {

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/face_recognition")
    public ResponseEntity<ApiResponse<Object>> startFaceRecognition() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName(); // Get the username from the security context
        if (username == null) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.<Object>builder().message("Not valid to confirm face recognition").build());
        }
        // Send user name to FASTAPI through api /face_recognition?username={username}
        String url = "http://localhost:8000/face_recognition/?username=" + username;
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
            return ResponseEntity.ok()
                    .body(ApiResponse.<Object>builder().message("Success to confirm face recognition").build());
        } catch (Exception e) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.<Object>builder().message("Fail to confirm face recognition").build());
        }

    }

}

@Getter
@Setter
class UserRequest {
    public String userName;
}
