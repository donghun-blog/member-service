package me.donghun.memberservice.domain.model;

import me.donghun.memberservice.common.EmptyParameters;
import me.donghun.memberservice.common.environment.AbstractDefaultTest;
import me.donghun.memberservice.domain.dto.MemberUpdateDomainDto;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_INTRODUCE_EMPTY;
import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_NAME_EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberTest extends AbstractDefaultTest {

    @DisplayName("회원소개 생성 시 회원 이름이 없는 경우 예외를 발생시킵니다.")
    @EmptyParameters
    void createMemberNameEmpty(String emptyName) {
        // given

        // when & then
        assertThatThrownBy(() -> MemberFixture.complete()
                                              .name(emptyName)
                                              .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_NAME_EMPTY);
    }

    @DisplayName("회원소개 생성 시 자기소개가 없는 경우 예외를 발생시킵니다.")
    @EmptyParameters
    void createMemberIntroduceEmpty(String introduce) {
        // given

        // when & then
        assertThatThrownBy(() -> MemberFixture.complete()
                                              .introduce(introduce)
                                              .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_INTRODUCE_EMPTY);
    }

    @DisplayName("회원소개 생성 시 회원 이미지, 회사가 없는 경우 null을 반환한다.")
    @EmptyParameters
    void createMemberEmptyNullReturn(String empty) {
        // given

        // when
        Member member = MemberFixture.complete()
                                     .avatar(empty)
                                     .company(empty)
                                     .build();
        // then
        assertThat(member)
                .extracting("avatar", "company")
                .containsExactly(null, null);
    }

    @DisplayName("회원 소개 변경 시 이름이 없는 경우 예외를 반환한다.")
    @EmptyParameters
    void updateEmptyName(String emptyName) {
        // given
        Member member = MemberFixture.complete()
                                     .build();
        MemberUpdateDomainDto memberUpdateDomainDto = MemberUpdateDomainDtoFixture.complete()
                                                                                  .name(emptyName)
                                                                                  .build();
        // when & then
        assertThatThrownBy(() -> member.update(memberUpdateDomainDto))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_NAME_EMPTY);
    }

    @DisplayName("회원 소개 변경 시 소개가 없는 경우 예외를 반환한다.")
    @EmptyParameters
    void updateEmptyIntroduce(String emptyIntroduce) {
        // given
        Member member = MemberFixture.complete()
                                     .build();
        MemberUpdateDomainDto memberUpdateDomainDto = MemberUpdateDomainDtoFixture.complete()
                                                                                  .introduce(emptyIntroduce)
                                                                                  .build();
        // when & then
        assertThatThrownBy(() -> member.update(memberUpdateDomainDto))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_INTRODUCE_EMPTY);
    }

    @DisplayName("회원 소개 변경 후 변경된 데이터를 확인한다.")
    @Test
    void update() {
        // given
        Member member = MemberFixture.complete()
                                     .build();
        MemberUpdateDomainDto memberUpdateDomainDto = MemberUpdateDomainDtoFixture.complete()
                                                                                  .build();
        // when
        member.update(memberUpdateDomainDto);

        // then
        assertThat(member)
                .isNotNull()
                .extracting("name", "occupationType", "company", "introduce")
                .contains(
                        memberUpdateDomainDto.getName(),
                        memberUpdateDomainDto.getOccupationType(),
                        memberUpdateDomainDto.getCompany(),
                        memberUpdateDomainDto.getIntroduce()
                );
        assertThat(member.getEmailAddress())
                .isNotNull()
                .extracting("email", "twitter", "linkedin", "github")
                .contains(
                        memberUpdateDomainDto.getEmailAddress()
                                             .getEmail(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getTwitter(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getLinkedin(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getGithub()
                );
        assertThat(member.getAvatar())
                .isNotNull()
                .extracting("path", "profileExtensionType")
                .containsExactly(
                        memberUpdateDomainDto.getAvatar(),
                        ProfileExtensionType.getExtension(memberUpdateDomainDto.getAvatar())
                );
    }

    @DisplayName("회원 소개 변경 시 프로필을 넘기지 않은 경우 기존 이미지 정보를 반환한다.")
    @Test
    void updateNoPassAvatar() {
        // given
        Member member = MemberFixture.complete()
                                     .build();
        MemberUpdateDomainDto memberUpdateDomainDto = MemberUpdateDomainDtoFixture.complete()
                                                                                  .avatar(null)
                                                                                  .build();
        // when
        member.update(memberUpdateDomainDto);

        // then
        assertThat(member)
                .isNotNull()
                .extracting("name", "occupationType", "company", "introduce")
                .contains(
                        memberUpdateDomainDto.getName(),
                        memberUpdateDomainDto.getOccupationType(),
                        memberUpdateDomainDto.getCompany(),
                        memberUpdateDomainDto.getIntroduce()
                );
        assertThat(member.getEmailAddress())
                .isNotNull()
                .extracting("email", "twitter", "linkedin", "github")
                .contains(
                        memberUpdateDomainDto.getEmailAddress()
                                             .getEmail(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getTwitter(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getLinkedin(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getGithub()
                );
        assertThat(member.getAvatar())
                .isNotNull()
                .extracting("path", "profileExtensionType")
                .containsExactly(
                        member.getAvatar()
                              .getPath(),
                        member.getAvatar()
                              .getProfileExtensionType()
                );
    }

    @DisplayName("회원 소개 변경 시 프로필을 넘기지 않은 경우 기존 이미지도 null인 경우 null값을 비교한다.")
    @NullSource
    @ParameterizedTest
    void updateNoPassAvatarDefaultNull(String avatar) {
        // given
        Member member = MemberFixture.complete()
                                     .avatar(avatar)
                                     .build();
        MemberUpdateDomainDto memberUpdateDomainDto = MemberUpdateDomainDtoFixture.complete()
                                                                                  .avatar(avatar)
                                                                                  .build();
        // when
        member.update(memberUpdateDomainDto);

        // then
        assertThat(member)
                .isNotNull()
                .extracting("name", "occupationType", "company", "introduce")
                .contains(
                        memberUpdateDomainDto.getName(),
                        memberUpdateDomainDto.getOccupationType(),
                        memberUpdateDomainDto.getCompany(),
                        memberUpdateDomainDto.getIntroduce()
                );
        assertThat(member.getEmailAddress())
                .isNotNull()
                .extracting("email", "twitter", "linkedin", "github")
                .contains(
                        memberUpdateDomainDto.getEmailAddress()
                                             .getEmail(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getTwitter(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getLinkedin(),
                        memberUpdateDomainDto.getEmailAddress()
                                             .getGithub()
                );
        assertThat(member.getAvatar())
                .isNull();
    }

    static class MemberUpdateDomainDtoFixture {
        public static MemberUpdateDomainDto.MemberUpdateDomainDtoBuilder complete() {
            return MemberUpdateDomainDto.builder()
                                        .name("update-name")
                                        .avatar("update-avatar.jpg")
                                        .occupationType(OccupationType.BACKEND_ENGINEER)
                                        .company("update-company")
                                        .email("update-email")
                                        .github("update-github")
                                        .twitter("update-twitter")
                                        .linkedin("update-linkedin")
                                        .introduce("update-introduce")
                    ;
        }
    }
}