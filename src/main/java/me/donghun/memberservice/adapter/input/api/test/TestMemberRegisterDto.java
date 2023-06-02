package me.donghun.memberservice.adapter.input.api.test;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TestMemberRegisterDto {
    private String email;
    private String nickName;
    private String password;

    @Builder
    private TestMemberRegisterDto(String email, String nickName, String password) {
        this.email = email;
        this.nickName = nickName;
        this.password = password;
    }
}
