package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.domain.model.Member;

public interface MemberQueryPort {
    Member findById(Long memberId);
    boolean isNicknameDuplicate(String nickName);
}
