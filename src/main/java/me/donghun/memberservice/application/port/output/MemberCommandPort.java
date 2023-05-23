package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.domain.model.Member;

public interface MemberCommandPort {
    Member save(Member member);

    void update(Member member);
}
