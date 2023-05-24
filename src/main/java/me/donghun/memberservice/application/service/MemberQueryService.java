package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.s3.config.S3Properties;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.application.port.input.MemberQueryUseCase;
import me.donghun.memberservice.application.port.output.MemberQueryPort;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberQueryService implements MemberQueryUseCase {

    private final MemberQueryPort memberQueryPort;
    private final S3Properties s3Properties;

    @Override
    public MemberDto getMemberById(Long memberId) {
        Member member = memberQueryPort.findById(memberId);
        return MemberDto.of(member, s3Properties.getAbsolutePath());
    }

    @Override
    public MemberDto getMemberByNickname(String nickName) {
        Member member = memberQueryPort.findByNickname(nickName);
        return MemberDto.of(member, s3Properties.getAbsolutePath());
    }
}
