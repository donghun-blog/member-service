package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.application.port.input.MemberAboutCommandUseCase;
import me.donghun.memberservice.application.port.output.LoadMemberPort;
import me.donghun.memberservice.application.port.output.SaveMemberPort;
import me.donghun.memberservice.application.port.output.UpdateMemberPort;
import me.donghun.memberservice.application.port.output.UploadAvatarPort;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.ProfileExtensionType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_AVATAR_EXTENSION_NOT_SUPPORT;

@Service
@RequiredArgsConstructor
public class MemberAboutCommandService implements MemberAboutCommandUseCase {

    private final SaveMemberPort saveMemberPort;
    private final UpdateMemberPort updateMemberPort;
    private final UploadAvatarPort uploadAvatarPort;
    private final LoadMemberPort loadMemberPort;
    private final RemoveAvatarImageService removeAvatarImageService;

    @Override
    public Long createAbout(MemberCreateCommand command) {
        String avatarPath = null;

        // 프로필 이미지 업로드 (상대 경로 반환)
        if(!command.avatar().isEmpty()) {
            String fileName = command.avatar().getOriginalFilename();
            if(StringUtils.hasText(fileName) && ProfileExtensionType.isSupport(fileName)) {
                avatarPath = uploadAvatarPort.upload(command.avatar());
            } else {
                throw new MemberException(MEMBER_AVATAR_EXTENSION_NOT_SUPPORT);
            }
        }

        Member member = command.toEntity(avatarPath);

        return saveMemberPort.save(member);
    }

    @Override
    public void updateAbout(Long memberId, MemberUpdateCommand command) {
        Member member = loadMemberPort.findById(memberId);

        String avatarPath = "";

        if (!command.avatar().isEmpty()) {
            avatarPath = uploadAvatarPort.upload(command.avatar());
            removeAvatarImageService.removeAsync(member.getAvatar().getPath());
        }

        member.update(command.toDomainDto(avatarPath));
        updateMemberPort.update(member);
    }
}
