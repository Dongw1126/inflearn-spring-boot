package bccard.payzintern.repository;

import bccard.payzintern.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 인터페이스가 인터페이스 받을땐 extends
// Entity 타입이랑, Id 타입 등록
public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {
    // select m from Member m where m.name = ?
    @Override
    Optional<Member> findByName(String name);
}
