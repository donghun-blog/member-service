package me.donghun.memberservice.adapter.output.persistence.command;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.application.port.output.SaveMemberPort;
import me.donghun.memberservice.application.port.output.UpdateMemberPort;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class MemberCommandAdapter implements SaveMemberPort, UpdateMemberPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Long save(Member member) {
        return memberRepository.save(memberMapper.toEntity(member))
                               .getId();
    }

    @Override
    @Transactional
    public void update(Member member) {
        MemberEntity memberEntity = memberRepository.findById(member.getId())
                                                    .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
        memberEntity.updateValue(member);
    }
}
