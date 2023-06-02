package me.donghun.memberservice.adapter.input.api.test;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "test_member")
public class TestMemberEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickName;
    private String password;

    @Builder
    private TestMemberEntity(Long id, String email, String nickName, String password) {
        this.id = id;
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
}
