package me.donghun.memberservice.application.dto;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.OccupationType;

@Getter
public class MemberDto {

    private Long id;
    private String name;
    private String profile;
    // 직업
    private OccupationType occupationType;
    // 회사
    private String company;
    private String email;
    private String github;
    private String twitter;
    private String linkedin;
    private String introduce;

    @Builder
    private MemberDto(Long id, String name, String profile, OccupationType occupationType, String company, String email, String github, String twitter, String linkedin, String introduce) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.occupationType = occupationType;
        this.company = company;
        this.github = github;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.introduce = introduce;
        this.email = email;
    }

    public static MemberDto of(Member member, String absolutePath) {
        return MemberDto.builder()
                        .id(member.getId())
                        .name(member.getName())
                        .profile(member.isProfileEmpty() ? null : absolutePath + member.getProfile())
                        .occupationType(member.getOccupation())
                        .company(member.getCompany())
                        .email(member.getEmail())
                        .github(member.getGithub())
                        .twitter(member.getTwitter())
                        .linkedin(member.getLinkedin())
                        .introduce(member.getIntroduce())
                        .build();
    }
}