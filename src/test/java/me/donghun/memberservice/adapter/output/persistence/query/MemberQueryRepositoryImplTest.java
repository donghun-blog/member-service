package me.donghun.memberservice.adapter.output.persistence.query;

import me.donghun.memberservice.DataAccessMysqlTestContainer;
import me.donghun.memberservice.adapter.output.persistence.command.MemberRepository;
import me.donghun.memberservice.adapter.output.persistence.entity.EmailAddressValue;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


class MemberQueryRepositoryImplTest extends DataAccessMysqlTestContainer {

    @Autowired
    private MemberQueryRepository memberQueryRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 조회 시 회원이 없는 경우 Optional의 null을 반환한다.")
    @Test
    void findByIdNotExist() {
        // given

        // when
        Optional<MemberEntity> findMember = memberQueryRepository.findById(1L);

        // then
        assertThat(findMember).isEmpty();
    }

    @DisplayName("회원 조회 후 회원 객체를 확인한다.")
    @Test
    void findById() {
        // given
        final Long id = 1L;
        final String name = "name";
        final MemberType memberType = MemberType.AUTHORS;
        final String avatar = "avatar";
        final OccupationType occupationType = OccupationType.BACKEND_ENGINEER;
        final String company = "company";

        final String email = "test@gmail.com";
        final String twitter = "twitter@twitter.com";
        final String linkedin = "linkedin@naver.com";
        final String github = "github@test.com";

        final EmailAddressValue emailAddressValue = EmailAddressValue.builder()
                                                                     .email(email)
                                                                     .twitter(twitter)
                                                                     .linkedin(linkedin)
                                                                     .github(github)
                                                                     .build();
        final String introduce = "introduce";

        MemberEntity memberEntity = MemberEntity.builder()
                                                .id(id)
                                                .name(name)
                                                .type(memberType)
                                                .avatar(avatar)
                                                .occupationType(occupationType)
                                                .company(company)
                                                .emailAddress(emailAddressValue)
                                                .introduce(introduce)
                                                .build();

        memberRepository.save(memberEntity);

        // when
        MemberEntity findMemberEntity = memberQueryRepository.findById(1L)
                                                             .orElseThrow();

        // then
        assertThat(findMemberEntity)
                .isNotNull()
                .extracting("id", "name", "type", "avatar", "occupationType", "company", "introduce")
                .contains(id, name, memberType, avatar, occupationType, company, introduce);

        assertThat(findMemberEntity.getEmailAddress()).extracting("email", "twitter", "linkedin", "github")
                                                      .contains(email, twitter, linkedin, github);

    }
}