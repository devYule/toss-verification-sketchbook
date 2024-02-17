package my.test.tosssketchbook.toss.requester;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import im.toss.cert.sdk.TossCertSession;
import im.toss.cert.sdk.TossCertSessionGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.test.tosssketchbook.toss.logic.TossVerificationRepository;
import my.test.tosssketchbook.toss.logic.model.CheckResponseVo;
import my.test.tosssketchbook.toss.logic.model.TossVerificationData;
import my.test.tosssketchbook.toss.logic.model.VerificationReadyVo;
import my.test.tosssketchbook.toss.requester.model.*;
import my.test.tosssketchbook.toss.requester.properties.TossVerificationProperties;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class TossVerificationRequester {

    private final TossCertSessionGenerator generator = new TossCertSessionGenerator();

    private final TossVerificationProperties tossVerificationProperties;
    private final ObjectMapper om;
    private final TossVerificationRepository repository;

    public VerificationReadyVo verificationRequest(UserInfo userInfo) {

        // toss 에 전달할 Dto 생성
        VerificationRequestDto dto = encode(userInfo);

        // 요청
        RestClient restClient = RestClient.builder()
                .baseUrl(tossVerificationProperties.getReadyRequestUrl())
                .build();

        String responseJSON = restClient.post()
                .header(tossVerificationProperties.getTokenKey(),
                        tossVerificationProperties.getTokenType() + tossVerificationProperties.getAccessToken())
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON).retrieve().body(String.class);

        VerificationReadyResponse verificationReadyResponse;
        try {
            verificationReadyResponse = om.readValue(responseJSON, VerificationReadyResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        String id = UUID.randomUUID().toString();
        repository.saveVerificationInfo(id, verificationReadyResponse.getSuccess().getTxId());
        return VerificationReadyVo.builder()
                .uuid(id)
                .resultType(verificationReadyResponse.getResultType())
                .build();
    }

    private VerificationRequestDto encode(UserInfo userInfo) {
        TossCertSession session = generator.generate();

        return VerificationRequestDto.builder()
                .userName(session.encrypt(userInfo.getUserName()))
                .userPhone(session.encrypt(userInfo.getUserPhone()))
                .userBirthday(session.encrypt(userInfo.getUserBirthday()))
                .sessionKey(session.getSessionKey())
                .requestType(tossVerificationProperties.getRequestType())
                .triggerType(tossVerificationProperties.getTriggerType())
                .successCallbackUrl(tossVerificationProperties.getSuccessCallbackUrl())
                .failCallbackUrl(tossVerificationProperties.getFailCallbackUrl())
                .build();


    }

    public CheckResponseVo check(String uuid) {
        TossCertSession session = generator.generate();
        String sessionKey = session.getSessionKey();

        TossVerificationData info = repository.findVerificationInfo(uuid);
        CheckRequestDto dto = CheckRequestDto.builder()
                .txId(info.getTxId())
                .sessionKey(sessionKey)
                .build();

        // 요청
        RestClient restClient = RestClient.builder()
                .baseUrl(tossVerificationProperties.getCheckRequestUrl())
                .build();

        String responseJSON = restClient.post()
                .header(tossVerificationProperties.getTokenKey(),
                        tossVerificationProperties.getTokenType() + tossVerificationProperties.getAccessToken())
                .body(dto)
                .contentType(MediaType.APPLICATION_JSON).retrieve().body(String.class);
        CheckResultDto resultDto;
        try {
            resultDto = om.readValue(responseJSON, CheckResultDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return decode(uuid, session, resultDto);
    }

    private CheckResponseVo decode(String uuid, TossCertSession session, CheckResultDto dto) {
        return CheckResponseVo.builder()
                .uuid(uuid)
                .name(session.decrypt(dto.getSuccess().getPersonalData().getName()))
                .gender(session.decrypt(dto.getSuccess().getPersonalData().getGender()))
                .birthday(session.decrypt(dto.getSuccess().getPersonalData().getBirthday()))
                .nationality(session.decrypt(dto.getSuccess().getPersonalData().getNationality()))
                .build();
    }


}
