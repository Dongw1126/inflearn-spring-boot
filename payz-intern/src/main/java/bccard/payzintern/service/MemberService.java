package bccard.payzintern.service;

import bccard.payzintern.domain.Member;
import bccard.payzintern.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


// @Service 어노테이션 붙여서 -> 스프링 컨테이너에 등록
// @Service
public class MemberService {
    // 회원 저장할 리포지토리
    private final MemberRepository memberRepository;

    // MemberRepository를 외부에서 넣어줌
    // MemberRepository가 필요하면 @AutoWired로 스프링 컨테이너가 넣어주게 (의존성 주입)
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 회원 가입
    public Long join(Member member) {
        // 같은 이름 허용 X
        // 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        // 이미 존재하면 에러 던짐
        // isPresent 메소드로 Optional로 감싸진 객체 확인
        memberRepository.findByName(member.getName())
            .ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });
    }

    // 전체 회원 조회
    public List<Member> findMember() {
        return memberRepository.findAll();
    }

    // id로 조회
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
