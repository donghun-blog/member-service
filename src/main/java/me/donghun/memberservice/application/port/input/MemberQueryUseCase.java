package me.donghun.memberservice.application.port.input;

import me.donghun.memberservice.application.dto.MemberDto;

public interface MemberQueryUseCase {
    MemberDto getMemberById(Long memberId);
    MemberDto getMemberByNickname(String nickName);

}
