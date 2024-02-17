package my.test.tosssketchbook.toss.requester.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "api.verification.toss")
public class TossVerificationProperties {
    private String accessToken;
    private String scope;
    private String tokenType;
    private String requestType;
    private String successCallbackUrl;
    private String failCallbackUrl;
    private String readyRequestUrl;
    private String checkRequestUrl;
    private String tokenKey;
    private String triggerType;

}
