package my.test.tosssketchbook.toss.logic.model;

import lombok.*;
import my.test.tosssketchbook.toss.requester.model.UserInfo;

@Getter
@Setter
@NoArgsConstructor
public class VerificationSuccessResponseVo extends VerificationResponseVo  {

    private UserInfo userInfo;

    public VerificationSuccessResponseVo(String resultType, UserInfo userInfo) {
        super(resultType);
        this.userInfo = userInfo;
    }

}
