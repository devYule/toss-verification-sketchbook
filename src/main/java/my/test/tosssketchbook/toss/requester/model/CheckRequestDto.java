package my.test.tosssketchbook.toss.requester.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CheckRequestDto {
    private String txId;
    private String sessionKey;
}
