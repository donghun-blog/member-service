package me.donghun.memberservice.application.dto;

import me.donghun.memberservice.domain.dto.UpdateMemberDomainModelDto;
import me.donghun.memberservice.domain.model.OccupationType;

public record MemberUpdateCommand(
        String name,
        String engName,
        String nickName,
        OccupationType occupationType,
        String company,
        String email,
        String twitter,
        String linkedin,
        String github,
        String introduce
) {
    public UpdateMemberDomainModelDto toDomainModelDto() {
        return UpdateMemberDomainModelDto.builder()
                                         .name(name)
                                         .engName(engName)
                                         .nickName(nickName)
                                         .email(email)
                                         .company(company)
                                         .introduce(introduce)
                                         .occupation(occupationType)
                                         .github(github)
                                         .twitter(twitter)
                                         .linkedin(linkedin)
                                         .build();
    }
}
