package bccard.payzintern;

import bccard.payzintern.aop.TimeTraceAop;
import bccard.payzintern.repository.*;
import bccard.payzintern.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// @Configuration을 읽고 -> 빈 등록 시작
@Configuration
public class SpringConfig {
/*
    private DataSource dataSource;

    // application.properties 보고
    // 스프링이 자체적을 datasource 빈 생성
    // dataSource를 스프링에서 생성자 주입
    @Autowired
    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }
 */
/*
    // JPA 쓸때 필요함
    private EntityManager em;
    
    // SpringConfig에서 EntityManager 주입
    // -> @Autowired 썼는데 원래는 @PersistenceContext 써야된다고 함
    @Autowired
    public SpringConfig(EntityManager em) {
         this.em = em;
    }
*/

    // 그냥 MemberRepository 만들어서 DI 받으면
    // 스프링 데이터 JPA가 알아서 구현체 만들어서 등록
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // @Bean 스프링 빈을 등록할거라는 의미
    // 메소드 이름으로 Bean 이름이 결정됨
    @Bean
    public MemberService memberService() {
        return new MemberService(memberRepository);
    }
    
/*    // AOP를 스프링 빈으로 직접 등록
    @Bean
    public TimeTraceAop timeTraceAop() {
        return new TimeTraceAop();
    }
*/

/*
    // 다른 로직은 건들지 않고 SpringConfig만 바꿈
    @Bean
    public MemberRepository memberRepository() {
        // 원하는 구현체로 갈아끼우기
        // return new MemoryMemberRepository();
        // return new JdbcMemberRepository(dataSource);
        // return new JdbcTemplateMemberRepository(dataSource);
        // return new JpaMemberRepository(em);
    }
*/

}
