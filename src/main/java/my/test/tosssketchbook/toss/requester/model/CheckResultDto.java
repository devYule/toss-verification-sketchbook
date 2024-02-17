package my.test.tosssketchbook.toss.requester.model;

import lombok.Data;

@Data
public class CheckResultDto {
    private String resultType;
    private CheckSuccess success;
}
