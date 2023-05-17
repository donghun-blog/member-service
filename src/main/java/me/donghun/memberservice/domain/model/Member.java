package me.donghun.memberservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.dto.MemberUpdateDomainDto;
import me.donghun.memberservice.domain.exception.MemberException;

import java.time.LocalDateTime;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NAME_EMPTY;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class Member {
    private Long id;
    private MemberType type;
    private String name;
    private String avatar;
    // 직업
    private OccupationType occupationType;
    // 회사
    private String company;
    private EmailAddress emailAddress;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String introduce;

    @Builder
    private Member(Long id, MemberType type, String name, String avatar, OccupationType occupationType, String company,
                   EmailAddress emailAddress, LocalDateTime createdAt, LocalDateTime modifiedAt,
                   String introduce) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.avatar = avatar;
        this.occupationType = occupationType;
        this.company = company;
        this.emailAddress = emailAddress;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.introduce = introduce;

        validate();
    }

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

    public void update(MemberUpdateDomainDto updateDomainDto) {
        updateName(updateDomainDto.getName());
        updateAvatar(updateDomainDto.getAvatar());
        updateOccupationType(updateDomainDto.getOccupationType());
        updateCompany(updateDomainDto.getCompany());
        updateEmailAddress(updateDomainDto.getEmailAddress());
        updateIntroduce(updateDomainDto.getIntroduce());
    }

    private void updateName(String name) {
        if(!this.name.equals(name)) {
            this.name = name;
        }
    }

    private void updateAvatar(String avatar) {
        if(!this.avatar.equals(avatar)) {
            this.avatar = avatar;
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

    private void validate() {
        if (!hasText(name)) throw new MemberException(MEMBER_NAME_EMPTY);
    }
}
