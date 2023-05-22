package me.donghun.memberservice.adapter.output.persistence.mapper;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    private final EmailAddressMapper emailAddressMapper;

    public Member toDomainModel(MemberEntity memberEntity) {

        return Member.builder()
                     .id(memberEntity.getId())
                     .name(memberEntity.getName())
                     .type(memberEntity.getType())
                     .avatar(memberEntity.getAvatar())
                     .occupationType(memberEntity.getOccupationType())
                     .company(memberEntity.getCompany())
                     .emailAddress(emailAddressMapper.toDomainModel(memberEntity.getEmailAddress()))
                     .createdAt(memberEntity.getCreatedAt())
                     .modifiedAt(memberEntity.getModifiedAt())
                     .introduce(memberEntity.getIntroduce())
                     .build();
    }

    public MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                           .id((isNull(member.getId()) ? null : member.getId()))
                           .type(member.getType())
                           .name(member.getName())
                           .avatar(
                                  isNull(member.getAvatar()) ? null : member.getAvatar().getPath()
                           )
                           .occupationType(member.getOccupationType())
                           .company(member.getCompany())
                           .emailAddress(emailAddressMapper.toValueObject(member.getEmailAddress()))
                           .introduce(member.getIntroduce())
                           .build();
    }

}
