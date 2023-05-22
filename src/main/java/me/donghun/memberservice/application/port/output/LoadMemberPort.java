package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.domain.model.Member;

public interface LoadMemberPort {
    Member findById(Long memberId);
}
