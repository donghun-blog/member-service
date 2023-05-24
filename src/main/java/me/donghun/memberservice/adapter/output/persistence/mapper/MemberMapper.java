package me.donghun.memberservice.adapter.output.persistence.mapper;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.persistence.entity.EmailAddressValue;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.domain.model.Member;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberMapper {

    public Member toDomainModel(MemberEntity memberEntity) {

        return Member.builder()
                     .id(memberEntity.getId())
                     .name(memberEntity.getName())
                     .engName(memberEntity.getEngName())
                     .nickName(memberEntity.getNickName())
                     .profile(memberEntity.getProfile())
                     .occupation(memberEntity.getOccupationType())
                     .company(memberEntity.getCompany())
                     .github(memberEntity.getEmailAddress()
                                         .getGithub())
                     .email(memberEntity.getEmailAddress()
                                        .getEmail())
                     .twitter(memberEntity.getEmailAddress()
                                          .getTwitter())
                     .linkedin(memberEntity.getEmailAddress()
                                           .getLinkedin())
                     .createdAt(memberEntity.getCreatedAt())
                     .modifiedAt(memberEntity.getModifiedAt())
                     .introduce(memberEntity.getIntroduce())
                     .build();
    }

    public MemberEntity toEntity(Member member) {
        return MemberEntity.builder()
                           .id(member.getId())
                           .name(member.getName())
                           .engName(member.getEngName())
                           .nickName(member.getNickName())
                           .profile(member.getProfile())
                           .occupationType(member.getOccupation())
                           .company(member.getCompany())
                           .emailAddress(
                                   EmailAddressValue.builder()
                                                    .github(member.getGithub())
                                                    .twitter(member.getTwitter())
                                                    .email(member.getEmail())
                                                    .linkedin(member.getLinkedin())
                                                    .build()
                           )
                           .introduce(member.getIntroduce())
                           .build();
    }

}
