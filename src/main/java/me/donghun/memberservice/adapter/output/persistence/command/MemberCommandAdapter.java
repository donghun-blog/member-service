package me.donghun.memberservice.adapter.output.persistence.command;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.application.port.output.MemberCommandPort;
import me.donghun.memberservice.domain.exception.MemberErrorCode;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberCommandAdapter implements MemberCommandPort {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = memberMapper.toEntity(member);
        return memberMapper.toDomainModel(memberRepository.save(memberEntity));
    }

    @Override
    @Transactional
    public void update(Member member) {
        MemberEntity memberEntity = memberRepository.findById(member.getId())
                                                    .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        memberEntity.update(member);
    }
}
