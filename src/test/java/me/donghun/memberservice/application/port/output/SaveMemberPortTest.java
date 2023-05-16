package me.donghun.memberservice.application.port.out;

import me.donghun.memberservice.DataAccessMysqlTestContainer;
import me.donghun.memberservice.adapter.output.persistence.command.MemberCommandAdapter;
import me.donghun.memberservice.adapter.output.persistence.command.MemberRepository;
import me.donghun.memberservice.adapter.output.persistence.mapper.EmailAddressMapper;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class SaveMemberPortTest extends DataAccessMysqlTestContainer {

    @TestConfiguration
    static class MapperConfiguration {

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
        public SaveMemberPort saveMemberPort() {
            return new MemberCommandAdapter(memberRepository, memberMapper());
        }
    }

    @Autowired
    private SaveMemberPort saveMemberPort;

    @DisplayName("회원 도메인에 대해서 저장 후 회원 id를 확인한다.")
    @Test
    void save() {
        // given
        Member memberDomainModel = MemberFixture.complete()
                                                .id(null)
                                                .build();

        // when
        Long memberId = saveMemberPort.save(memberDomainModel);

        // then
        assertThat(memberId)
                .isNotZero()
                .isNotNull();
    }

    static class MemberFixture {
        public static Member.MemberBuilder complete() {
            return Member.builder()
                         .id(1L)
                         .type(MemberType.AUTHORS)
                         .name("tester")
                         .avatar("avatar")
                         .occupationType(OccupationType.BACKEND_ENGINEER)
                         .company("company")
                         .emailAddress(EmailAddress.createEmailAddress(
                                 "test@gmail.com",
                                 "twitter@test.com",
                                 "test@linkedin.com",
                                 "test@github"
                         ))
                         .introduce("안녕하세요 자기소개")
                    ;
        }
    }
}