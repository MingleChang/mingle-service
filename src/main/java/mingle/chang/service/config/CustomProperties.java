package mingle.chang.service.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = CustomProperties.PREFIX)
public class CustomProperties {
    public static final String PREFIX = "custom";
}
