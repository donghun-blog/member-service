package me.donghun.memberservice.domain.model;

import me.donghun.memberservice.common.environment.AbstractDefaultTest;
import me.donghun.memberservice.domain.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_AVATAR_EXTENSION_NOT_SUPPORT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProfileExtensionTypeTest extends AbstractDefaultTest {

    @DisplayName("지원하지 않는 확장자인경우 false를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test.svg", "test.GIF"})
    void isSupportFalse(String extension) {
        // given

        // when
        boolean result = ProfileExtensionType.isSupport(extension);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("지원하는 확장자인경우 true를 반환한다.")
    @EnumSource(ProfileExtensionType.class)
    @ParameterizedTest
    void isSupport(ProfileExtensionType extension) {
        // given

        // when
        boolean result = ProfileExtensionType.isSupport(extension.name());

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("확장자 반환 시 지원하지 않는 확장자인 경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"test.svg", "test.GIF"})
    void getExtensionNotSupport(String extension) {
        // given

        // when & then
        assertThatThrownBy(() -> ProfileExtensionType.getExtension(extension))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_AVATAR_EXTENSION_NOT_SUPPORT);
    }

    @DisplayName("확장자 반환 시 지원하는 확장자인 경우 type을 반환한다.")
    @EnumSource(ProfileExtensionType.class)
    @ParameterizedTest
    void getExtension(ProfileExtensionType extension) {
        // given

        // when
        ProfileExtensionType result = ProfileExtensionType.getExtension(extension.name());

        // then
        assertThat(result).isEqualByComparingTo(extension);
    }
}