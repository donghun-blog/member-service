package me.donghun.memberservice.application.dto;

import me.donghun.memberservice.domain.dto.MemberUpdateDomainDto;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

public record MemberUpdateCommand(String name,
                                  MultipartFile avatar,
                                  String occupationType,
                                  String company,
                                  String email,
                                  String twitter,
                                  String linkedin,
                                  String github,
                                  String introduce) {

    public MemberUpdateDomainDto toDomainDto(String avatarPath) {
        return MemberUpdateDomainDto.builder()
                                    .name(name)
                                    .avatar((StringUtils.hasText(avatarPath)) ? avatarPath : null)
                                    .occupationType(OccupationType.valueOf(occupationType))
                                    .company(company)
                                    .email(email)
                                    .twitter(twitter)
                                    .linkedin(linkedin)
                                    .github(github)
                                    .introduce(introduce)
                                    .build();
    }
}
