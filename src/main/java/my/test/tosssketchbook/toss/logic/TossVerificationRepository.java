package my.test.tosssketchbook.toss.logic;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import my.test.tosssketchbook.toss.logic.model.Member;
import my.test.tosssketchbook.toss.logic.model.TossVerificationData;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class TossVerificationRepository {
    private final EntityManager em;

    @Transactional
    public TossVerificationData saveVerificationInfo(String uuid, String txId) {
        TossVerificationData data = TossVerificationData.builder()
                .id(uuid)
                .txId(txId)
                .build();
        em.persist(data);
        return data;

    }

    public TossVerificationData findVerificationInfo(String uuid) {
        return em.find(TossVerificationData.class, uuid);
    }
}
