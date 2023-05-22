package me.donghun.memberservice.application.service;

import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.output.s3.config.S3Properties;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.common.environment.AbstractMysqlTestContainer;
import me.donghun.memberservice.fixture.MemberEntityFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;

class MemberAboutQueryServiceTest extends AbstractMysqlTestContainer {

    @Autowired
    private MemberAboutQueryService memberAboutQueryService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private S3Properties s3Properties;

    @ContextConfiguration
    static class MemberAboutQueryServiceConfiguration {
        @Bean
        S3Properties s3Properties() {
            S3Properties properties = new S3Properties();
            properties.setBucket("bucket");
            properties.setPrefix("prefix");
            properties.setBaseUrl("baseUrl");
            return properties;
        }
    }

    @DisplayName("회원 조회 후 MemberDto 객체를 확인한다.")
    @Test
    void getMemberInfo() {
        // given
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .build();
        memberRepository.save(memberEntity);

        // when
        MemberDto memberDto = memberAboutQueryService.getMemberInfo(memberEntity.getId());

        // then
        assertThat(memberDto).isNotNull()
                             .extracting("id", "type", "name", "avatar", "occupationType", "company", "introduce")
                             .contains(
                                     memberEntity.getId(),
                                     memberEntity.getType(),
                                     memberEntity.getName(),
                                     s3Properties.getAbsolutePath() + memberEntity.getAvatar(),
                                     memberEntity.getOccupationType(),
                                     memberEntity.getCompany(),
                                     memberEntity.getIntroduce()
                             );
        assertThat(memberDto.getEmailAddress()).isNotNull()
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
    }

    @DisplayName("")
    @Test
    void updateAbout() {
        // given

        // when

        // then
    }
}