package me.donghun.memberservice.application.port.input;

import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;

public interface MemberCommandUseCase {
    Long create(MemberCreateCommand command);

    void updateById(Long memberId, MemberUpdateCommand command);

    void updateByNickname(String nickName, MemberUpdateCommand command);
}
