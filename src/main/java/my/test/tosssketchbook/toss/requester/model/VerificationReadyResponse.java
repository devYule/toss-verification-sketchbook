package my.test.tosssketchbook.toss.requester.model;

import lombok.Getter;

@Getter
public class VerificationReadyResponse {
    private String resultType;
    private Success success;
    private Fail fail;
}
