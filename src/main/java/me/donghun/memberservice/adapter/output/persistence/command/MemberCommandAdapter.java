package me.donghun.memberservice.adapter.output.persistence.command;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.application.port.out.SaveMemberPort;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberCommandAdapter implements SaveMemberPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    @Override
    public Long save(Member member) {
        return memberRepository.save(memberMapper.toEntity(member)).getId();
    }
}
