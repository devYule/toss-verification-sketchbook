package my.test.tosssketchbook.toss.logic;

import lombok.RequiredArgsConstructor;
import my.test.tosssketchbook.toss.logic.model.CheckResponseVo;
import my.test.tosssketchbook.toss.logic.model.VerificationFailResponseVo;
import my.test.tosssketchbook.toss.logic.model.VerificationReadyVo;
import my.test.tosssketchbook.toss.logic.model.VerificationSuccessResponseVo;
import my.test.tosssketchbook.toss.requester.model.UserInfo;
import my.test.tosssketchbook.toss.requester.model.VerificationReadyResponse;
import org.springframework.web.bind.annotation.*;

/**
 * 전체흐름:
 * (사전에 accessToken 발급 필요 - 테스트 환경은 만료시간 1년으로 제공됨)
 * 1. 사용자가 요청한 이름, 핸드폰번호, 생년원일 로 토스에 준비요청 보냄
 * 2. 유효한 정보면 토스가 해당 사용자의 모바일로 인증링크를 보냄(카카오톡으로)
 * 3. uuid + txId 를 db에 저장해둠 (uuid 로 식별했음) , uuid 리턴함.
 * 4. 사용자가 인증 후, 인증완료했다고 우리사이트의 버튼을 누른다고 가정함.
 * 5. 해당 버튼을 누를시 인증완료 체크를 하는 우리서버의 컨트롤러로 요청, 이때 이전에 리턴된 uuid 를 함께 가져옴.
 * 6. 해당 uuid 로 txId 를 찾아서 토스에 인증완료 되었는지 확인 요청 보냄
 * 7. 요청이 완료되었으면 해당 유저의 다양한 정보가 리턴됨.
 * 8. 우리서버는 해당 정보에서 필요한 정보 (uuid, 이름, 성별, 생년월일, 지역) 를 리턴함.
 * 9. 차후 회원가입시 uuid 를 또다시 보내게 되고, 그러면 앞서 저장해둔 db의 txId 를 가진 테이블과 조인을 통해
 *      해당 유저의 본인인증 정보를 조회할 수 있음.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/verification/toss")
public class TossVerificationController {

    private final TossVerificationService service;

    @PostMapping("/verification")
    public VerificationReadyVo verification(@RequestBody UserInfo userInfo) {
        return service.request(userInfo);
    }

    @GetMapping("/check")
    public CheckResponseVo check(@RequestParam String uuid) {
        return service.check(uuid);
    }


    @PostMapping("/success")
    public VerificationSuccessResponseVo success(UserInfo vo) {
        return new VerificationSuccessResponseVo("SUCCESS", vo);
    }

    @PostMapping("/fail")
    public VerificationFailResponseVo fail(VerificationReadyResponse vo) {
        return new VerificationFailResponseVo("FAIL", vo.getFail().getReason());
    }

}
