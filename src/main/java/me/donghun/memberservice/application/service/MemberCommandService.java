package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.application.port.input.MemberCommandUseCase;
import me.donghun.memberservice.application.port.output.MemberCommandPort;
import me.donghun.memberservice.application.port.output.MemberQueryPort;
import me.donghun.memberservice.application.port.output.UploadProfilePort;
import me.donghun.memberservice.domain.exception.MemberErrorCode;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NICKNAME_DUPLICATE;

@Service
@RequiredArgsConstructor
public class MemberCommandService implements MemberCommandUseCase {

    private final MemberCommandPort memberCommandPort;
    private final MemberQueryPort memberQueryPort;
    private final UploadProfilePort uploadProfilePort;

    @Override
    @Transactional
    public Long create(MemberCreateCommand command) {

        if(memberQueryPort.isNicknameDuplicate(command.nickName())) {
            throw new MemberException(MEMBER_NICKNAME_DUPLICATE);
        }

        Member member = Member.Factory.create(command.toDomainModelDto());

        if(!member.isProfileEmpty()) {
            uploadProfilePort.upload(member.getProfile(), command.profile());
        }

        return memberCommandPort.save(member).getId();
    }

    @Override
    public void updateById(Long memberId, MemberUpdateCommand command) {

        if(memberQueryPort.isNicknameDuplicate(command.nickName())) {
            throw new MemberException(MEMBER_NICKNAME_DUPLICATE);
        }

        Member member = memberQueryPort.findById(memberId);
        member.update(command.toDomainModelDto());

        memberCommandPort.update(member);
    }

    @Override
    public void updateByNickname(String nickName, MemberUpdateCommand command) {
        if(memberQueryPort.isNicknameDuplicate(command.nickName())) {
            throw new MemberException(MEMBER_NICKNAME_DUPLICATE);
        }

        Member member = memberQueryPort.findByNickname(nickName);
        member.update(command.toDomainModelDto());

        memberCommandPort.update(member);
    }
}
