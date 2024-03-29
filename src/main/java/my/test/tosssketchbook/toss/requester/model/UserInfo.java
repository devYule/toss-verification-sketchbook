package my.test.tosssketchbook.toss.requester.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserInfo {
    // 암호화 필요 데이터 시작
    private String userName;
    private String userPhone;
    private String userBirthday;
    // 암호화 필요 데이터 끝
}
