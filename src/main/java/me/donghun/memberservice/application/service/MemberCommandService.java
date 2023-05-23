package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.application.port.input.MemberCommandUseCase;
import me.donghun.memberservice.application.port.output.MemberCommandPort;
import me.donghun.memberservice.application.port.output.MemberQueryPort;
import me.donghun.memberservice.application.port.output.UploadProfilePort;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {

    private final MemberCommandPort memberCommandPort;
    private final MemberQueryPort memberQueryPort;
    private final UploadProfilePort uploadProfilePort;

    @Override
    @Transactional
    public Long create(MemberCreateCommand command) {
        Member member = Member.Factory.create(command.toDomainModelDto());

        if(!member.isProfileEmpty()) {
            uploadProfilePort.upload(member.getProfile(), command.profile());
        }

        return memberCommandPort.save(member).getId();
    }

    @Override
    @Transactional
    public void update(Long memberId, MemberUpdateCommand command) {
        Member member = memberQueryPort.findById(memberId);
        member.update(command.toDomainModelDto());

        memberCommandPort.update(member);
    }
}
