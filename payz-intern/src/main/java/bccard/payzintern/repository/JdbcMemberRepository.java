package bccard.payzintern.repository;

import bccard.payzintern.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMemberRepository implements MemberRepository {
    // DB에 붙을라면 datasource가 필요함
    private final DataSource dataSource;

    // 스프링이 접속 정보를 만들어 놓고  datasource를 주입해줌
    // application.property 세팅한거
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        // 회원 생성 쿼리 -> ?부분은 PreparedStatement 파라미터 바인딩
        // 변수보단 상수로 빼는게 낫긴 함
        String sql = "insert into member(name) values(?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            // datasource 에서 커넥션을 가져옴
            conn = getConnection();
            // sql을 넣어주고, DB에서 생성한 id 값을 얻을려할때 사용
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // 첫 번째 ?에 이름을 넣어줌
            pstmt.setString(1, member.getName());

            // DB에 쿼리 날리기
            pstmt.executeUpdate();

            // 생성된 키를 꺼냄
            rs = pstmt.getGeneratedKeys();
            // next로 하나씩 접근
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            // executeQuery로 select문 쓰고 resultSet 바로 받아옴
            rs = pstmt.executeQuery();
            
            // 결과 값이 있으면
            if (rs.next()) {
                // member 객체 만들어서 반환
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));

                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            
            // 쿼리 실행해서 나온거 전부 리스트에 담에서 반환
            while (rs.next()) {
                // 각 결과값 member로 만들어서 리스트에 넣어줌
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }

            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    
    // DataSource에서 직접 getConnection하면 매번 새로운 커넥션이 이루어짐
    // DataSourceUtils를 통해서 하면, Connection을 동일하게 유지해줌
    // close도 DataSourceUtils로 구현
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // null인지 하나씩 체크하면서 닫아줌
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // 닫을 때도 DataSourceUtils 통해서 닫음
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}