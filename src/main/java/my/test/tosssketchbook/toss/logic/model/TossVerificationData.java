package my.test.tosssketchbook.toss.logic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TossVerificationData {

    @Id
//    @Column(length = 200)
    private String id;
    @Column(length = 200)
    private String txId;

}
