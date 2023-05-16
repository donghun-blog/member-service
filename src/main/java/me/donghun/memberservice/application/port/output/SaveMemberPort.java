package me.donghun.memberservice.application.port.out;

import me.donghun.memberservice.domain.model.Member;

public interface SaveMemberPort {
    Long save(Member member);
}
