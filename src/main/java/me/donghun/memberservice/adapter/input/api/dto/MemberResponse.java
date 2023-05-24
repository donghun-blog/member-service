package me.donghun.memberservice.adapter.input.api.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.application.dto.MemberDto;

@Getter
public class MemberResponse {

    private final Long memberId;
    private final String name;
    private final String engName;
    private final String nickName;
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
    private MemberResponse(Long memberId, String name, String engName, String nickName, String avatar, String occupation, String occupationName, String company, String email, String twitter, String linkedin, String github,
                           String introduce) {
        this.memberId = memberId;
        this.name = name;
        this.engName = engName;
        this.nickName = nickName;
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
                             .name(memberDto.getName())
                             .engName(memberDto.getEngName())
                             .nickName(memberDto.getNickName())
                             .avatar(memberDto.getProfile())
                             .occupation(memberDto.getOccupationType()
                                                  .getDescription())
                             .occupationName(memberDto.getOccupationType()
                                                      .getName())
                             .company(memberDto.getCompany())
                             .email(memberDto.getEmail())
                             .twitter(memberDto.getTwitter())
                             .linkedin(memberDto.getLinkedin())
                             .github(memberDto.getGithub())
                             .introduce(memberDto.getIntroduce())
                             .build();
    }
}