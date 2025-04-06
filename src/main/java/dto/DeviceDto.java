package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class DeviceDto {

    @Builder
    @Getter
    @Setter
    static public class AddDeviceRequestDto {
        private String HomeId;
        private String deviceName;
        private String deviceNumber;
        private String deviceType;
        private String deviceStatus;
    }
}