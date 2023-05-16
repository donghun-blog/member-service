package me.donghun.memberservice.application.dto;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;

@Getter
public class MemberDto {

    private Long id;
    private MemberType type;
    private String name;
    private String avatar;
    // 직업
    private OccupationType occupationType;
    // 회사
    private String company;
    private EmailAddress emailAddress;
    private String introduce;

    @Builder
    private MemberDto(Long id, MemberType type, String name, String avatar, OccupationType occupationType, String company, EmailAddress emailAddress,
                      String introduce) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.avatar = avatar;
        this.occupationType = occupationType;
        this.company = company;
        this.emailAddress = emailAddress;
        this.introduce = introduce;
    }

    public static MemberDto of(Member member, String avatarAbsolutePath) {
        return MemberDto.builder()
                        .id(member.getId())
                        .type(member.getType())
                        .name(member.getName())
                        .avatar(avatarAbsolutePath + member.getAvatar())
                        .occupationType(member.getOccupationType())
                        .company(member.getCompany())
                        .emailAddress(member.getEmailAddress())
                        .introduce(member.getIntroduce())
                        .build();
    }
}
