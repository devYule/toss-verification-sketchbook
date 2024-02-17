package my.test.tosssketchbook.toss.logic.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class VerificationFailResponseVo extends VerificationResponseVo {

    private String reason;

    public VerificationFailResponseVo(String resultType, String reason) {
        super(resultType);
        this.reason = reason;
    }
}
