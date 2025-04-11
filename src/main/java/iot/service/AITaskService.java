package iot.service;

import java.util.List;

public interface AITaskService {
    public boolean sendEmbeddingForIdentification(String username); // Send embedding to fastapi
                                                                    // for identification

    public boolean sendUsernameForRegistration(String username); // Send embedding to fastapi for registration
}
