package me.donghun.memberservice.application.port.out;

import me.donghun.memberservice.domain.model.Member;

public interface LoadMemberPort {
    Member findById(Long memberId);
}
