package me.donghun.memberservice.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;

import java.time.LocalDateTime;
import java.util.Objects;

import static java.util.Objects.isNull;
import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_INTRODUCE_EMPTY;
import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NAME_EMPTY;
import static org.springframework.util.StringUtils.hasText;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private MemberType type;
    private String name;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private OccupationType occupationType;
    private String company;
    @Embedded
    private EmailAddressValue emailAddress;

    @Lob
    private String introduce;

    @Builder
    private MemberEntity(Long id, MemberType type, String name, String avatar, OccupationType occupationType, String company, EmailAddressValue emailAddress, String introduce,
                         LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.avatar = avatar;
        this.occupationType = occupationType;
        this.company = company;
        this.emailAddress = emailAddress;
        this.introduce = introduce;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;

        validate();
    }

    public void updateValue(Member member) {
        if(!this.name.equals(member.getName())) this.name = member.getName();

        if(!isNull(member.getAvatar())) {
            if(!this.avatar.equals(member.getAvatar().getPath())) this.avatar = member.getAvatar().getPath();
        }

        if(!this.company.equals(member.getCompany())) this.company = member.getCompany();
        if(!this.introduce.equals(member.getIntroduce())) this.introduce = member.getIntroduce();

        if(!this.emailAddress.isEqual(member.getEmailAddress())) {
            this.emailAddress = EmailAddressValue.createEmailAddressValue(member.getEmailAddress());
        }

        validate();
    }

    private void validate() {
        if(!hasText(this.name)) throw new MemberException(MEMBER_NAME_EMPTY);
        if(!hasText(this.introduce)) throw new MemberException(MEMBER_INTRODUCE_EMPTY);
    }

}
