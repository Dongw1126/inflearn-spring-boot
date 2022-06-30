package bccard.payzintern;

import bccard.payzintern.repository.MemberRepository;
import bccard.payzintern.repository.MemoryMemberRepository;
import bccard.payzintern.service.MemberService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration을 읽고 -> 빈 등록 시작
@Configuration
public class SpringConfig {
    // @Bean 스프링 빈을 등록할거라는 의미
    // 메소드 이름으로 Bean 이름이 결정됨
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository());
    }

    @Bean
    public MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

}
