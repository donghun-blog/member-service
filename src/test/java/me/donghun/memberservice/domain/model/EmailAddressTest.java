package me.donghun.memberservice.domain.model;

import me.donghun.memberservice.filters.DefaultTest;
import me.donghun.memberservice.filters.EmptyParameters;
import me.donghun.memberservice.domain.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_EMAIL_EMPTY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EmailAddressTest extends DefaultTest {

    @DisplayName("이메일 주소 목록 생성 시 회원 이메일이 없을 경우 예외를 발생시킨다.")
    @EmptyParameters
    void createEmailAddressEmailEmpty(String emptyEmail) {
        // given

        // when & then
        assertThatThrownBy(() -> EmailAddressFixture.complete()
                                                    .email(emptyEmail)
                                                    .build())
                .isInstanceOf(MemberException.class)
                .hasFieldOrPropertyWithValue("errorCode", MEMBER_EMAIL_EMPTY);
    }
    
    @DisplayName("이메일 검증 시 회원 이메일이 맞지 않을 경우 false를 반환한다.")
    @Test
    void isEqualsNotEmail() {
        // given
        EmailAddress originalEmailAddress = EmailAddressFixture.complete().build();
        EmailAddress updateEmailAddress = EmailAddressFixture.complete().email("default@gmail.com").build();

        // when
        boolean result = originalEmailAddress.isEquals(updateEmailAddress);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이메일 검증 시 회원 트위터가 맞지 않을 경우 false를 반환한다.")
    @Test
    void isEqualsNotTwitter() {
        // given
        EmailAddress originalEmailAddress = EmailAddressFixture.complete().build();
        EmailAddress updateEmailAddress = EmailAddressFixture.complete().twitter("default@twitter.com").build();

        // when
        boolean result = originalEmailAddress.isEquals(updateEmailAddress);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이메일 검증 시 회원 링크드인 맞지 않을 경우 false를 반환한다.")
    @Test
    void isEqualsNotLinkedin() {
        // given
        EmailAddress originalEmailAddress = EmailAddressFixture.complete().build();
        EmailAddress updateEmailAddress = EmailAddressFixture.complete().linkedin("default@linkedin.com").build();

        // when
        boolean result = originalEmailAddress.isEquals(updateEmailAddress);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("이메일 검증 시 회원 깃허브가 맞지 않을 경우 false를 반환한다.")
    @Test
    void isEqualsNotGithub() {
        // given
        EmailAddress originalEmailAddress = EmailAddressFixture.complete().build();
        EmailAddress updateEmailAddress = EmailAddressFixture.complete().github("default@github.com").build();

        // when
        boolean result = originalEmailAddress.isEquals(updateEmailAddress);

        // then
        assertThat(result).isFalse();
    }

    @DisplayName("기존 이메일과 변경할 이메일의 데이터가 같은 경우 true를 반환한다.")
    void isEquals() {
        // given
        EmailAddress originalEmailAddress = EmailAddressFixture.complete().build();
        EmailAddress updateEmailAddress = EmailAddressFixture.complete().build();

        // when
        boolean result = originalEmailAddress.isEquals(updateEmailAddress);

        // then
        assertThat(result).isTrue();
    }

    public static class EmailAddressFixture {
        public static EmailAddress.EmailAddressBuilder complete() {
            return EmailAddress.builder()
                               .email("test@gmail.com")
                               .github("test@github.com")
                               .twitter("test@twitter.com")
                               .linkedin("test@linkedin.com")
                    ;
        }
    }
}