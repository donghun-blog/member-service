package me.donghun.memberservice.application.port.input;

import me.donghun.memberservice.application.dto.MemberDto;

public interface MemberAboutQueryUseCase {
    MemberDto getMemberInfo(Long memberId);
}
