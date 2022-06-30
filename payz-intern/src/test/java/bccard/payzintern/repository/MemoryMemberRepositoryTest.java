package bccard.payzintern.repository;

import bccard.payzintern.domain.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

// static import 써서 바로 메소드 쓸 수 있게
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MemoryMemberRepositoryTest {

    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 테스트가 끝날 때마다 호출
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    // JUnit 테스트케이스 어노테이션
    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member);

        // Optional에서 값 꺼낼때 get으로 꺼냄
        Member result = repository.findById(member.getId()).get();

        // Assertions.assertEquals로 결과 검증
        // expected에 기대하는 값, actual에 실제 결과값을 넣어주면 됨
        // Assertions.assertEquals(member, result);
         assertThat(result).isEqualTo(member);
    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();
        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();

        assertThat(result.size()).isEqualTo(2);
    }
}
