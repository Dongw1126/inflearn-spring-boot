package bccard.payzintern.service;

import bccard.payzintern.domain.Member;
import bccard.payzintern.repository.MemberRepository;
import bccard.payzintern.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// 스프링 부트에서 테스트
@SpringBootTest
// Transactional 안붙이면 테스트 한 쿼리들이 그대로 DB에 들어감
// -> 테스트를 반복할 수 없게됨
@Transactional
class MemberServiceIntegrationTest {
    // 테스트는 제일 끝단에 있는 코드라 그냥 편한거로
    // 구현체는 SpringConfig 한데서 올라올거임
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member();
        member.setName("hello");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    void 중복_회원_예외() {
        // given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        // when
        memberService.join(member1);

        // then
        // assertThrows로 예외 체크 -> 어떤 예외인지, 어떤 로직을 태울지
        // 람다 함수로 넣어줌
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }
}