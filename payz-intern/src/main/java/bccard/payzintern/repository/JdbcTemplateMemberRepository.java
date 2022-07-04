package bccard.payzintern.repository;

import bccard.payzintern.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    // JdbcTemplate을 주입받는 것이 아니라 dataSource를 주입받음
    // dataSource로 jdbcTemplate 객체 생성
    // (생성자가 하나면 @Autowired 생략가능)
    @Autowired
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        // SimpleJdbcInsert쓰면 -> 쿼리 짤 필요없이 알아서 만들어줌
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        // PK 생성
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");
        
        // Map에다 key-value 값 넣어줌
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        // 쿼리 실행 -> 키 반환 -> member에 키 세팅
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 결과 나오는걸 RowMapper로 매핑해줘야함
        // 뒤에 넘기는 건 바인딩할 파라미터 값
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        // 옵셔널로 반환
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByName(String name) {
        // 바인딩할 파라미터 값을 넣어줘야함
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return null;
    }
    
    private RowMapper<Member> memberRowMapper() {
        // 람다 스타일
        return (rs, rowNum) -> {
             Member member = new Member();
             member.setId(rs.getLong("id"));
             member.setName(rs.getString("name"));
             return  member;
        }; 
    }
}
