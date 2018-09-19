package com.cimarasah.auth.security.jwt;

import org.springframework.mobile.device.Device;
import org.springframework.stereotype.Component;

@Component
public class AudienceProvider {

    public String generateAudience(Device device) {
        String audience = "unknown";

        if (device.isNormal()) {
            audience = "web";
        } else if (device.isTablet()) {
            audience = "tablet";
        } else if (device.isMobile()) {
            audience = "mobile";
        }

        return audience;
    }
}
