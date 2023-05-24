package me.donghun.memberservice.adapter.output.persistence.query;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberQueryRepository;
import me.donghun.memberservice.application.port.output.MemberQueryPort;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Component;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class MemberQueryAdapter implements MemberQueryPort {

    private final MemberQueryRepository memberQueryRepository;
    private final MemberMapper memberMapper;

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberQueryRepository.findById(memberId)
                                                         .orElseThrow(() -> new MemberException(MEMBER_NOT_FOUND));
        return memberMapper.toDomainModel(memberEntity);
    }

    @Override
    public boolean isNicknameDuplicate(String nickName) {
        return memberQueryRepository.isNicknameDuplicate(nickName);
    }
}
