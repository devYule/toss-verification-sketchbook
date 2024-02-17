package my.test.tosssketchbook.toss.logic.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VerificationReadyVo {
    private String resultType;
    private String uuid;
}
