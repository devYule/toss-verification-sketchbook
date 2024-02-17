package my.test.tosssketchbook.toss.logic;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.test.tosssketchbook.toss.logic.model.CheckResponseVo;
import my.test.tosssketchbook.toss.logic.model.VerificationReadyVo;
import my.test.tosssketchbook.toss.requester.TossVerificationRequester;
import my.test.tosssketchbook.toss.requester.model.UserInfo;
import my.test.tosssketchbook.toss.requester.model.VerificationReadyResponse;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TossVerificationService {

    private final TossVerificationRequester requester;

    public VerificationReadyVo request(UserInfo userInfo) {
        return requester.verificationRequest(userInfo);
    }

    public CheckResponseVo check(String uuid) {

        return requester.check(uuid);

    }
}
