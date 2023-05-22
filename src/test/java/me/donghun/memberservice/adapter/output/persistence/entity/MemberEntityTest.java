package me.donghun.memberservice.adapter.output.persistence.entity;

import me.donghun.memberservice.common.environment.AbstractDefaultTest;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.Profile;
import me.donghun.memberservice.domain.model.ProfileExtensionType;
import me.donghun.memberservice.fixture.MemberEntityFixture;
import me.donghun.memberservice.fixture.MemberFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MemberEntityTest extends AbstractDefaultTest {

    private final String name = "name";
    private final String avatar = "avatar.png";
    private final String company = "company";
    private final String introduce = "introduce";

    @Mock
    private EmailAddressValue mockEmailAddressValue;

    @DisplayName("MemberEntity 업데이트 시 이름이 다른 경우 이름만 업데이트된다.")
    @Test
    void updateValueName() {
        // given
        final String beforeName = "beforeName";
        final String afterName = "afterName";
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .name(beforeName)
                                                       .avatar(avatar)
                                                       .company(company)
                                                       .introduce(introduce)
                                                       .emailAddress(mockEmailAddressValue)
                                                       .build();

        Member member = MemberFixture.complete()
                                     .name(afterName)
                                     .avatar(avatar)
                                     .company(company)
                                     .introduce(introduce)
                                     .build();

        given(mockEmailAddressValue.isEqual(any()))
                .willReturn(true);
        // when
        memberEntity.updateValue(member);

        // then
        assertThat(member)
                .extracting("name",  "company", "introduce")
                .contains(afterName, company, introduce);
        assertThat(member.getAvatar())
                .isNotNull()
                .extracting("path", "profileExtensionType")
                .containsExactly(avatar, ProfileExtensionType.getExtension(avatar));
    }

    @DisplayName("MemberEntity 업데이트 시 이미지 경로가 다른 경우 이미지 경로만 업데이트된다.")
    @Test
    void updateValueAvatar() {
        // given
        final String beforeAvatar = "beforeAvatar.png";
        final String afterAvatar = "afterAvatar.png";
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .name(name)
                                                       .avatar(beforeAvatar)
                                                       .company(company)
                                                       .introduce(introduce)
                                                       .emailAddress(mockEmailAddressValue)
                                                       .build();

        Member member = MemberFixture.complete()
                                     .name(name)
                                     .avatar(afterAvatar)
                                     .company(company)
                                     .introduce(introduce)
                                     .build();

        given(mockEmailAddressValue.isEqual(any()))
                .willReturn(true);
        // when
        memberEntity.updateValue(member);

        // then
        assertThat(member)
                .extracting("name", "company", "introduce")
                .contains(name, company, introduce);
        assertThat(member.getAvatar())
                .isNotNull()
                .extracting("path", "profileExtensionType")
                .containsExactly(afterAvatar, ProfileExtensionType.getExtension(afterAvatar));
    }

    @DisplayName("MemberEntity 업데이트 시 회사가 다른 경우 회사만 업데이트된다.")
    @Test
    void updateValueCompany() {
        // given
        final String beforeCompany = "beforeCompany";
        final String afterCompany = "afterCompany";
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .name(name)
                                                       .avatar(avatar)
                                                       .company(beforeCompany)
                                                       .introduce(introduce)
                                                       .emailAddress(mockEmailAddressValue)
                                                       .build();

        Member member = MemberFixture.complete()
                                     .name(name)
                                     .avatar(avatar)
                                     .company(afterCompany)
                                     .introduce(introduce)
                                     .build();

        given(mockEmailAddressValue.isEqual(any()))
                .willReturn(true);
        // when
        memberEntity.updateValue(member);

        // then
        assertThat(member)
                .extracting("name", "company", "introduce")
                .contains(name, afterCompany, introduce);
        assertThat(member.getAvatar())
                .isNotNull()
                .extracting("path", "profileExtensionType")
                .containsExactly(avatar, ProfileExtensionType.getExtension(avatar));
    }

    @DisplayName("MemberEntity 업데이트 시 소개가 다른 경우 소개만 업데이트된다.")
    @Test
    void updateValueIntroduce() {
        // given
        final String beforeIntroduce = "beforeIntroduce";
        final String afterIntroduce = "afterIntroduce";
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .name(name)
                                                       .avatar(avatar)
                                                       .company(company)
                                                       .introduce(beforeIntroduce)
                                                       .emailAddress(mockEmailAddressValue)
                                                       .build();

        Member member = MemberFixture.complete()
                                     .name(name)
                                     .avatar(avatar)
                                     .company(company)
                                     .introduce(afterIntroduce)
                                     .build();

        given(mockEmailAddressValue.isEqual(any()))
                .willReturn(true);
        // when
        memberEntity.updateValue(member);

        // then
        assertThat(member)
                .extracting("name", "company", "introduce")
                .contains(name, company, afterIntroduce);
        assertThat(member.getAvatar())
                .isNotNull()
                .extracting("path", "profileExtensionType")
                .containsExactly(avatar, ProfileExtensionType.getExtension(avatar));
    }
}