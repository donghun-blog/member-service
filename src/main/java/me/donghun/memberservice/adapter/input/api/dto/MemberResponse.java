package me.donghun.memberservice.adapter.input.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.application.dto.MemberDto;

@Getter
public class MemberResponse {

    private final Long memberId;
    private final String type;
    private final String name;
    private final String avatar;
    private final String occupation;
    private final String occupationName;
    private final String company;
    private final String email;
    private final String twitter;
    private final String linkedin;
    private final String github;
    private final String introduce;

    @Builder(access = AccessLevel.PROTECTED)
    private MemberResponse(Long memberId, String type, String name, String avatar, String occupation, String occupationName, String company, String email, String twitter, String linkedin, String github,
                           String introduce) {
        this.memberId = memberId;
        this.type = type;
        this.name = name;
        this.avatar = avatar;
        this.occupation = occupation;
        this.occupationName = occupationName;
        this.company = company;
        this.email = email;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.github = github;
        this.introduce = introduce;
    }

    public static BaseResponse<MemberResponse> success(MemberDto memberDto) {
        return BaseResponse.success(MemberResponse.of(memberDto));
    }

    private static MemberResponse of(MemberDto memberDto) {
        return MemberResponse.builder()
                             .memberId(memberDto.getId())
                             .type(memberDto.getType()
                                            .name())
                             .name(memberDto.getName())
                             .avatar(memberDto.getAvatar())
                             .occupation(memberDto.getOccupationType()
                                                  .getDescription())
                             .occupationName(memberDto.getOccupationType()
                                                      .getName())
                             .company(memberDto.getCompany())
                             .email(memberDto.getEmailAddress()
                                             .getEmail())
                             .twitter(memberDto.getEmailAddress()
                                               .getTwitter())
                             .linkedin(memberDto.getEmailAddress()
                                                .getLinkedin())
                             .github(memberDto.getEmailAddress()
                                              .getGithub())
                             .introduce(memberDto.getIntroduce())
                             .build();
    }
}
