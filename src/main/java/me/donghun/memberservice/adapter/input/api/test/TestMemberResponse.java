package me.donghun.memberservice.adapter.input.api.test;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TestMemberResponse {
    private Long id;
    private String email;
    private String nickName;

    @Builder
    private TestMemberResponse(String email, String nickName, Long id) {
        this.email = email;
        this.nickName = nickName;
        this.id = id;
    }
}
