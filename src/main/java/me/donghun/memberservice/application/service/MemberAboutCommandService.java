package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.application.port.in.MemberAboutCommandUseCase;
import me.donghun.memberservice.application.port.out.LoadMemberPort;
import me.donghun.memberservice.application.port.out.SaveMemberPort;
import me.donghun.memberservice.application.port.out.UploadAvatarPort;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberAboutCommandService implements MemberAboutCommandUseCase {

    private final SaveMemberPort saveMemberPort;
    private final UploadAvatarPort uploadAvatarPort;
    private final LoadMemberPort loadMemberPort;

    @Override
    public Long createAbout(MemberCreateCommand command) {

        // 프로필 이미지 업로드 (상대 경로 반환)
        String avatarPath = uploadAvatarPort.upload(command.avatar());
        Member member = command.toEntity(avatarPath);

        return saveMemberPort.save(member);
    }

    @Override
    public void updateAbout(Long memberId, MemberUpdateCommand command) {
        Member member = loadMemberPort.findById(memberId);

        String avatarPath = "";

        if (!member.getAvatar().equals(command.avatar().getName())) {
            avatarPath = uploadAvatarPort.upload(command.avatar());
        }

        member.update(command.toDomainDto(avatarPath));
        saveMemberPort.save(member);
    }
}
