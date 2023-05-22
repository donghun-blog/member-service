package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.domain.model.Member;

public interface UpdateMemberPort {
    void update(Member member);
}
