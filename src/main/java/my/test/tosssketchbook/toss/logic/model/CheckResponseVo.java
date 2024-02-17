package my.test.tosssketchbook.toss.logic.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CheckResponseVo {
    private String uuid;
    private String name;
    private String birthday;
    private String gender;
    private String nationality;
}
