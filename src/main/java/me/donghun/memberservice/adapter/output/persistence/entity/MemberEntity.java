package me.donghun.memberservice.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.OccupationType;

import java.time.LocalDateTime;

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

    private String name;
    private String profile;
    @Enumerated(EnumType.STRING)
    private OccupationType occupationType;
    private String company;
    @Embedded
    private EmailAddressValue emailAddress;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String introduce;

    @Builder
    private MemberEntity(Long id, String name, String profile, OccupationType occupationType, String company, EmailAddressValue emailAddress, String introduce,
                         LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.occupationType = occupationType;
        this.company = company;
        this.emailAddress = emailAddress;
        this.introduce = introduce;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.deletedAt = deletedAt;

        validate();
    }
    
    private void validate() {
        if (!hasText(this.name)) throw new MemberException(MEMBER_NAME_EMPTY);
        if (!hasText(this.introduce)) throw new MemberException(MEMBER_INTRODUCE_EMPTY);
    }

    public void update(Member member) {
        this.name = member.getName();
        this.introduce = member.getIntroduce();
        this.occupationType = member.getOccupation();
        this.company = member.getCompany();
        this.emailAddress = EmailAddressValue.builder()
                                             .email(member.getEmail())
                                             .twitter(member.getTwitter())
                                             .github(member.getGithub())
                                             .linkedin(member.getLinkedin())
                                             .build();
    }
}
