package me.donghun.memberservice.application.port.in;

import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;

public interface MemberAboutCommandUseCase {
    Long createAbout(MemberCreateCommand command);

    void updateAbout(Long memberId, MemberUpdateCommand command);

}
