package me.donghun.memberservice.application.port.output;

import me.donghun.memberservice.common.environment.AbstractDataAccessMysqlTestContainer;
import me.donghun.memberservice.adapter.output.persistence.command.MemberCommandAdapter;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.adapter.output.persistence.mapper.EmailAddressMapper;
import me.donghun.memberservice.adapter.output.persistence.mapper.MemberMapper;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import static org.assertj.core.api.Assertions.assertThat;

class SaveMemberPortTest extends AbstractDataAccessMysqlTestContainer {

    @TestConfiguration
    static class SaveMemberPortConfiguration {

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

}