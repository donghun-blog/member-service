package me.donghun.memberservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.dto.CreateMemberDomainModelDto;
import me.donghun.memberservice.domain.dto.UpdateMemberDomainModelDto;
import me.donghun.memberservice.domain.exception.MemberErrorCode;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.service.ProfileRandomUUIDGenerateDomainService;

import java.time.LocalDateTime;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.*;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class Member {
    private Long id;
    private String name;
    private String engName;
    private String nickName;
    private String profile;
    private OccupationType occupation;
    private String company;
    private String email;
    private String github;
    private String twitter;
    private String linkedin;
    private String introduce;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Builder
    private Member(Long id, String name, String engName, String nickName, String profile, OccupationType occupation, String company, String email, String github, String twitter, String linkedin, String introduce, LocalDateTime createdAt, LocalDateTime modifiedAt) {

        if (!hasText(name)) {
            throw new MemberException(MemberErrorCode.MEMBER_NAME_EMPTY);
        }

        if (!hasText(email)) {
            throw new MemberException(MEMBER_EMAIL_EMPTY);
        }

        if (!hasText(introduce)) {
            throw new MemberException(MEMBER_INTRODUCE_EMPTY);
        }

        if (hasText(profile) && !ProfileSupportType.isSupport(profile)) {
            throw new MemberException(MEMBER_PROFILE_EXTENSION_NOT_SUPPORT);
        }

        if (!hasText(nickName)) {
            throw new MemberException(MEMBER_NICKNAME_EMPTY);
        }

        this.id = id;
        this.name = name;
        this.engName = engName;
        this.nickName = nickName;
        this.email = email;
        this.introduce = introduce;

        this.company = company;
        this.occupation = occupation;
        this.profile = hasText(profile) ? profile : null;
        this.github = hasText(github) ? github : null;
        this.twitter = hasText(twitter) ? twitter : null;
        this.linkedin = hasText(linkedin) ? linkedin : null;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public boolean isProfileEmpty() {
        return !hasText(profile);
    }

    public void update(UpdateMemberDomainModelDto updateDomainModelDto) {
        if (!hasText(updateDomainModelDto.name())) {
            throw new MemberException(MemberErrorCode.MEMBER_NAME_EMPTY);
        }

        if (!hasText(updateDomainModelDto.email())) {
            throw new MemberException(MEMBER_EMAIL_EMPTY);
        }

        if (!hasText(updateDomainModelDto.introduce())) {
            throw new MemberException(MEMBER_INTRODUCE_EMPTY);
        }

        if(!hasText(updateDomainModelDto.nickName())) {
            throw new MemberException(MEMBER_NICKNAME_EMPTY);
        }

        this.name = updateDomainModelDto.name();
        this.engName = updateDomainModelDto.engName();
        this.nickName = updateDomainModelDto.nickName();
        this.email = updateDomainModelDto.email();
        this.introduce = updateDomainModelDto.introduce();
        this.occupation = updateDomainModelDto.occupation();
        this.github = updateDomainModelDto.github();
        this.twitter = updateDomainModelDto.twitter();
        this.linkedin = updateDomainModelDto.linkedin();
        this.company = updateDomainModelDto.company();
    }

    public static class Factory {
        public static Member create(CreateMemberDomainModelDto domainModelDto) {

            String profile = null;
            if (hasText(domainModelDto.profile())) {
                profile = ProfileRandomUUIDGenerateDomainService.generate(domainModelDto.profile());
            }

            return Member.builder()
                         .name(domainModelDto.name())
                         .engName(domainModelDto.engName())
                         .nickName(domainModelDto.nickName())
                         .email(domainModelDto.email())
                         .introduce(domainModelDto.introduce())
                         .occupation(domainModelDto.occupation())
                         .profile(profile)
                         .company(domainModelDto.company())
                         .github(domainModelDto.github())
                         .twitter(domainModelDto.twitter())
                         .linkedin(domainModelDto.linkedin())
                         .build();
        }

    }

}
