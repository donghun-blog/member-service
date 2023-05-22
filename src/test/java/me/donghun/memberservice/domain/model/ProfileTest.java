package me.donghun.memberservice.domain.model;

import me.donghun.memberservice.common.EmptyParameters;
import me.donghun.memberservice.common.environment.AbstractDefaultTest;
import me.donghun.memberservice.domain.exception.MemberException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ProfileTest extends AbstractDefaultTest {

    @DisplayName("회원 프로필 생성 시 프로필 경로가 존재하지 않는 경우 예외를 반환한다.")
    @EmptyParameters
    void createProfileEmptyPath(String emptyPath) {
        // given
    
        // when & then
        assertThatThrownBy(() -> Profile.createProfile(emptyPath))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_AVATAR_EMPTY);
    }

    @DisplayName("회원 프로필 생성 시 잘못된 확장자인경우 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"123123", "testpng", "test,png", "test.svg", "test.gif"})
    void createProfileExtensionValidFail(String extension) {
        // given

        // when & then
        assertThatThrownBy(() -> Profile.createProfile(extension))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_AVATAR_EXTENSION_NOT_SUPPORT);
    }

    @DisplayName("회원 프로필 생성 시 잘못된 확장자인경우 예외를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "test.png, PNG",
            "test.jpg, JPG",
            "test.jpeg, JPEG"
    })
    void createProfileExtension(String path, ProfileExtensionType type) {
        // given

        // when
        Profile result = Profile.createProfile(path);

        // then
        assertThat(result).isNotNull()
                          .extracting("path", "profileExtensionType")
                          .containsExactly(path, type);
    }

    @DisplayName("프로필 이미지 비교 시 다른 경우 false를 반환한다.")
    @Test
    void isPathEqualFalse() {
        // given
        final String originalPath = "test1.png";
        final String comparePath = "test2.jpg";
        final Profile originalProfile = Profile.createProfile(originalPath);

        // when
        boolean result = originalProfile.isPathEqual(comparePath);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("프로필 경로를 통해 확장자를 반환하는데 지원하지 않는 확장자인 경우에는 예외를 반환한다.")
    @ParameterizedTest
    @ValueSource(strings = {"123123", "testpng", "test,png", "test.svg", "test.gif"})
    void getExtension(String path) {
        // given

        // when & then
        assertThatThrownBy(() -> ProfileExtensionType.getExtension(path))
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_AVATAR_EXTENSION_NOT_SUPPORT);
    }
}