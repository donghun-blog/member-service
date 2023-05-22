package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.output.persistence.mapper.EmailAddressMapper;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.adapter.output.persistence.query.MemberQueryAdapter;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberQueryRepository;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.common.environment.AbstractDataAccessMysqlTestContainer;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.Profile;
import me.donghun.memberservice.domain.model.ProfileExtensionType;
import me.donghun.memberservice.fixture.MemberEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LoadMemberPortTest extends AbstractDataAccessMysqlTestContainer {

    @TestConfiguration
    static class LoadMemberPortConfiguration {

        @Autowired
        private MemberQueryRepository memberQueryRepository;

        @Bean
        public MemberMapper memberMapper() {
            return new MemberMapper(emailAddressMapper());
        }

        @Bean
        public EmailAddressMapper emailAddressMapper() {
            return new EmailAddressMapper();
        }

        @Bean
        public LoadMemberPort loadMemberPort() {
            return new MemberQueryAdapter(memberQueryRepository, memberMapper());
        }
    }

    @Autowired
    private LoadMemberPort loadMemberPort;
    @Autowired
    private MemberRepository memberRepository;


    @DisplayName("회원 조회 시 회원이 없는 경우 예외를 발생시킨다.")
    @Test
    void findByIdMemberNotFound() {
        // given

        // when & then
        assertThatThrownBy(() -> loadMemberPort.findById(1L))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_NOT_FOUND);
    }

    @DisplayName("회원 조회 시 회원이 있는 경우 회원 객체를 확인한다.")
    @Test
    void findById() {
        // given
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .id(null)
                                                       .build();
        memberRepository.save(memberEntity);

        // when
        Member findMember = loadMemberPort.findById(memberEntity.getId());

        // then
        assertThat(findMember).isNotNull()
                              .extracting("id", "type", "name", "occupationType", "company", "createdAt", "modifiedAt", "introduce", "createdAt", "modifiedAt")
                              .contains(
                                      memberEntity.getId(),
                                      memberEntity.getType(),
                                      memberEntity.getName(),
                                      memberEntity.getOccupationType(),
                                      memberEntity.getCompany(),
                                      memberEntity.getCreatedAt(),
                                      memberEntity.getModifiedAt(),
                                      memberEntity.getIntroduce(),
                                      memberEntity.getCreatedAt(),
                                      memberEntity.getModifiedAt()
                              );
        assertThat(findMember.getEmailAddress()).isNotNull()
                                                .extracting("email", "twitter", "linkedin", "github")
                                                .contains(
                                                        memberEntity.getEmailAddress()
                                                                    .getEmail(),
                                                        memberEntity.getEmailAddress()
                                                                    .getTwitter(),
                                                        memberEntity.getEmailAddress()
                                                                    .getLinkedin(),
                                                        memberEntity.getEmailAddress()
                                                                    .getGithub()
                                                );
        assertThat(findMember.getAvatar()).isNotNull()
                                          .extracting("path", "profileExtensionType")
                                          .containsExactly(memberEntity.getAvatar(), ProfileExtensionType.getExtension(memberEntity.getAvatar()));
    }

}