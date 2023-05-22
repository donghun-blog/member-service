package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.adapter.output.persistence.command.MemberCommandAdapter;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.output.persistence.mapper.EmailAddressMapper;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.common.environment.AbstractDataAccessMysqlTestContainer;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.fixture.EmailAddressFixture;
import me.donghun.memberservice.fixture.MemberEntityFixture;
import me.donghun.memberservice.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UpdateMemberPortTest extends AbstractDataAccessMysqlTestContainer {


    @TestConfiguration
    static class UpdateMemberPortConfiguration {

        @Autowired
        private MemberRepository memberRepository;

        @Bean
        public MemberMapper memberMapper() {
            return new MemberMapper(emailAddressMapper());
        }

        @Bean
        public EmailAddressMapper emailAddressMapper() {
            return new EmailAddressMapper();
        }

        @Bean
        public UpdateMemberPort updateMemberPort() {
            return new MemberCommandAdapter(memberRepository, memberMapper());
        }
    }

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private UpdateMemberPort updateMemberPort;
    @Autowired
    private MemberMapper memberMapper;

    @DisplayName("회원 업데이트 시 회원이 없는 경우 예외를 발생시킨다.")
    @Test
    void updateMemberNotFound() {
        // given
        Member member = MemberFixture.complete()
                                     .build();

        // when & then
        assertThatThrownBy(() -> updateMemberPort.update(member))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_NOT_FOUND)
        ;
    }

    @DisplayName("회원 업데이트 후 영속성 컨테스트에 업데이트된 데이터를 확인한다.")
    @Test
    void update() {
        // given
        final String updateName = "updateName";
        final String updateAvatar = "updateAvatar.png";
        final String updateCompany = "updateCompany";

        final String updateEmail = "update@gmail.com";
        final String updateTwitter = "update@twitter.com";
        final String updateGithub = "update@github.com";
        final String updateLinkedIn = "update@linkedin.com";
        final EmailAddress updateEmailAddress = EmailAddressFixture.complete()
                                                                   .email(updateEmail)
                                                                   .twitter(updateTwitter)
                                                                   .github(updateGithub)
                                                                   .linkedin(updateLinkedIn)
                                                                   .build();
        final String updateIntroduce = "updateIntroduce";

        // 영속성 컨텍스트에 Member Entity 저장
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .id(null)
                                                       .build();
        memberRepository.save(memberEntity);

        // 업데이트할 Member Domain Model
        Member member = MemberFixture.complete()
                                     .id(memberEntity.getId())
                                     .type(memberEntity.getType())
                                     .name(updateName)
                                     .avatar(updateAvatar)
                                     .company(updateCompany)
                                     .emailAddress(updateEmailAddress)
                                     .introduce(updateIntroduce)
                                     .build();

        // when
        updateMemberPort.update(member);

        // then
        MemberEntity findMemberEntity = memberRepository.findById(memberEntity.getId())
                                                        .orElseThrow();
        assertThat(findMemberEntity)
                .isNotNull()
                .extracting("id", "name", "avatar", "company", "introduce")
                .contains(
                        member.getId(),
                        updateName,
                        updateAvatar,
                        updateCompany,
                        updateIntroduce
                );
        assertThat(findMemberEntity.getEmailAddress())
                .isNotNull()
                .extracting("email", "twitter", "github", "linkedin")
                .contains(updateEmail, updateTwitter, updateGithub, updateLinkedIn);

    }
}