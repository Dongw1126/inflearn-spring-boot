package bccard.payzintern.domain;

import javax.persistence.*;

// JPA가 관리하는 Entity라는 것을 표현
@Entity
public class Member {
    // PK 매핑
    @Id
    // DB가 자동으로 생성 -> identity 전략
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 컬럼명이 다르면 아래처럼 어노테이션 붙이면 됨 -> @Column(name = "username")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
