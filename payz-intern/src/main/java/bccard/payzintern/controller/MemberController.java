package bccard.payzintern.controller;

import bccard.payzintern.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MemberController {
    private final MemberService memberService;

    // 스프링에서 MemberController 객체를 생성할 때,
    // @Autowired 붙이면 스프링에 있는 memberService를 연결해줌
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
}
