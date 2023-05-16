package me.donghun.memberservice.domain.dto;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.domain.model.OccupationType;

@Getter
public class MemberUpdateDomainDto {
    private final String name;
    private final String avatar;
    private final OccupationType occupationType;
    private final String company;
    private final EmailAddress emailAddress;
    private final String introduce;

    @Builder
    private MemberUpdateDomainDto(String name, String avatar, OccupationType occupationType, String company, String email, String twitter, String linkedin, String github, String introduce) {
        this.name = name;
        this.avatar = avatar;
        this.occupationType = occupationType;
        this.company = company;
        this.emailAddress = EmailAddress.builder()
                                        .email(email)
                                        .twitter(twitter)
                                        .linkedin(linkedin)
                                        .github(github)
                                        .build();
        this.introduce = introduce;
    }
}
