package my.test.tosssketchbook.toss.requester.model;

import lombok.Data;

@Data
public class CheckSuccess {
    private String txId;
    private String status;
    private PersonalData personalData;
}
