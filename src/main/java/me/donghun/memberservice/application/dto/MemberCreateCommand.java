package me.donghun.memberservice.application.dto;

import lombok.Builder;
import me.donghun.memberservice.domain.dto.CreateMemberDomainModelDto;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

public record MemberCreateCommand(
        String name,
        String email,
        String introduce,
        OccupationType occupationType,
        MultipartFile profile,
        String github,
        String twitter,
        String linkedin
) {

    @Builder
    public MemberCreateCommand {
    }

    public CreateMemberDomainModelDto toDomainModelDto() {
        return CreateMemberDomainModelDto.builder()
                                         .name(name)
                                         .email(email)
                                         .introduce(introduce)
                                         .occupation(occupationType)
                                         .profile(Objects.isNull(profile) ? null : profile.getOriginalFilename())
                                         .github(github)
                                         .twitter(twitter)
                                         .linkedin(linkedin)
                                         .build();
    }

}
