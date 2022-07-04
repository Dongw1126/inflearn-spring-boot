package bccard.payzintern.repository;

import bccard.payzintern.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {
    // JPA는 EntityManager라는 걸로 동작
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }


    @Override
    public Member save(Member member) {
        // persist -> 영구 저장의 의미
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 클래스랑 PK 넣어주면 find가 알아서 조회
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // PK 아닐 땐 다른 객체지향 쿼리 언어 사용 -> JPQL
        // :name으로 변수를 넣어주고
        // setParameter로 바인딩
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        // 테이블이 아니라 Entity 대상으로 쿼리를 날림 (Member m = Member as m)
        // 객체 자체를 Select -> 좀더 공부
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }
}