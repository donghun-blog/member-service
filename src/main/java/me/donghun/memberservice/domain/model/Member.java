package me.donghun.memberservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.dto.MemberUpdateDomainDto;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.utils.CheckEmptyValueUtils;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.isNull;
import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_INTRODUCE_EMPTY;
import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NAME_EMPTY;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class Member {
    private Long id;
    private MemberType type;
    private String name;
    private Profile avatar;
    // 직업
    private OccupationType occupationType;
    // 회사
    private String company;
    private EmailAddress emailAddress;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String introduce;

    public static Member createMember(String name,
                                      String avatar,
                                      MemberType memberType,
                                      OccupationType occupationType,
                                      String company,
                                      EmailAddress emailAddress,
                                      String introduce) {

        return Member.builder()
                     .id(null)
                     .type(memberType)
                     .name(name)
                     .avatar(avatar)
                     .occupationType(occupationType)
                     .company(company)
                     .emailAddress(emailAddress)
                     .introduce(introduce)
                     .build();
    }

    @Builder
    private Member(Long id, MemberType type, String name, String avatar, OccupationType occupationType, String company,
                   EmailAddress emailAddress, LocalDateTime createdAt, LocalDateTime modifiedAt,
                   String introduce) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.occupationType = occupationType;
        this.emailAddress = emailAddress;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.company = CheckEmptyValueUtils.checkEmptyValue(company);
        this.introduce = CheckEmptyValueUtils.checkEmptyValue(introduce);

        if(hasText(avatar)) {
            this.avatar = Profile.createProfile(avatar);
        }

        validate();
    }

    public void update(MemberUpdateDomainDto updateDomainDto) {
        updateName(updateDomainDto.getName());
        updateAvatar(updateDomainDto.getAvatar());
        updateOccupationType(updateDomainDto.getOccupationType());
        updateCompany(updateDomainDto.getCompany());
        updateEmailAddress(updateDomainDto.getEmailAddress());
        updateIntroduce(updateDomainDto.getIntroduce());

        validate();
    }

    private void validate() {
        if (!hasText(name)) throw new MemberException(MEMBER_NAME_EMPTY);
        if (!hasText(introduce)) throw new MemberException(MEMBER_INTRODUCE_EMPTY);
    }

    private void updateName(String name) {
        if(!this.name.equals(name)) {
            this.name = name;
        }
    }

    private void updateAvatar(String path) {
        if(hasText(path)) {
            this.avatar = Profile.createProfile(path);
        }
    }

    private void updateOccupationType(OccupationType occupationType) {
        if(!this.occupationType.equals(occupationType)) {
            this.occupationType = occupationType;
        }
    }

    private void updateCompany(String company) {
        if(!this.company.equals(company)) {
            this.company = company;
        }
    }

    private void updateEmailAddress(EmailAddress emailAddress) {
        if(!this.emailAddress.isEquals(emailAddress)) {
            this.emailAddress = EmailAddress.createEmailAddress(emailAddress);
        }
    }

    private void updateIntroduce(String introduce) {
        if(!this.introduce.equals(introduce)) {
            this.introduce = introduce;
        }
    }
}
