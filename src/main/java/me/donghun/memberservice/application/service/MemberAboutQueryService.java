package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.s3.config.S3Properties;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.application.port.input.MemberAboutQueryUseCase;
import me.donghun.memberservice.application.port.out.LoadMemberPort;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberAboutQueryService implements MemberAboutQueryUseCase {

    private final LoadMemberPort loadMemberPort;
    private final S3Properties s3Properties;

    @Override
    public MemberDto getMemberInfo(Long memberId) {
        Member member = loadMemberPort.findById(memberId);
        return MemberDto.of(member, s3Properties.getAbsolutePath());
    }
}
