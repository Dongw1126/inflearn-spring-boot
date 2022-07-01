package bccard.payzintern.controller;

import bccard.payzintern.domain.Member;
import bccard.payzintern.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    // 스프링에서 MemberController 객체를 생성할 때,
    // @Autowired 붙이면 스프링에 있는 memberService를 연결해줌
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


    // url에 직접 치는건 Get방식 → @GetMapping으로 감
    // -> 그래서 createForm으로 들어가는거
    @GetMapping("/members/new")
    public String createForm() {
        // 템플릿의 members 폴더의 createMemberForm.html로 이동
        return "members/createMemberForm";
    }
    
    // form의 action으로 여기 매핑된 url로 오는 데,
    // Post 방식으로 오면 얘가 처리
    @PostMapping("/members/new")
    // createMemberForm.html의 input 태그의 name=”name”과 매칭
    // 스프링이 보고 알아서 MemberForm의 setName 호출해서 넣어줌 -> 좀 더 자세히 알아보기
    public String create(MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

         memberService.join(member);

         // 다 끝나고 홈화면으로 리다이렉트
         return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // 모든 멤버 가져와서 attribute로 넣어줌
        List<Member> members = memberService.findMember();
        model.addAttribute("members", members);

        return "members/memberList";
    }

}
