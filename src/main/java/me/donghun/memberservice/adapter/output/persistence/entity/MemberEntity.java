package me.donghun.memberservice.adapter.output.persistence.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "member")
public class MemberEntity extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @Lob
    private String introduce;

    @Builder
    private MemberEntity(Long id, MemberType type, String name, String avatar, OccupationType occupationType, String company, EmailAddressValue emailAddress, LocalDateTime createdAt, LocalDateTime modifiedAt, String introduce) {
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
    }
}
