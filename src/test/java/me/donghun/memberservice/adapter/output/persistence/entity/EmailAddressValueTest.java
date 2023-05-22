package me.donghun.memberservice.adapter.output.persistence.entity;

import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.fixture.EmailAddressFixture;
import me.donghun.memberservice.fixture.EmailAddressValueFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmailAddressValueTest {

    private final String email = "email";
    private final String twitter = "twitter";
    private final String linkedin = "linkedin";
    private final String github = "github";

    @DisplayName("EmailAddressValue에서 Email 비교 시 다를경우 false를 반환한다.")
    @Test
    void isEqualNotEmailEqual() {
        // given
        EmailAddressValue originalValue = EmailAddressValueFixture.complete()
                                                                  .email(email)
                                                                  .twitter(twitter)
                                                                  .linkedin(linkedin)
                                                                  .github(github)
                                                                  .build();
        EmailAddress compareValue = EmailAddressFixture.complete()
                                                       .email("change Email")
                                                       .twitter(twitter)
                                                       .linkedin(linkedin)
                                                       .github(github)
                                                       .build();
        // when
        boolean result = originalValue.isEqual(compareValue);

        // then
        assertFalse(result);
    }

    @DisplayName("EmailAddressValue에서 Twitter 비교 시 다를경우 false를 반환한다.")
    @Test
    void isEqualNotTwitterEqual() {
        // given
        EmailAddressValue originalValue = EmailAddressValueFixture.complete()
                                                                  .email(email)
                                                                  .twitter(twitter)
                                                                  .linkedin(linkedin)
                                                                  .github(github)
                                                                  .build();
        EmailAddress compareValue = EmailAddressFixture.complete()
                                                       .email(email)
                                                       .twitter("change twitter")
                                                       .linkedin(linkedin)
                                                       .github(github)
                                                       .build();
        // when
        boolean result = originalValue.isEqual(compareValue);

        // then
        assertFalse(result);
    }

    @DisplayName("EmailAddressValue에서 linkedin 비교 시 다를경우 false를 반환한다.")
    @Test
    void isEqualNotLinkedInEqual() {
        // given
        EmailAddressValue originalValue = EmailAddressValueFixture.complete()
                                                                  .email(email)
                                                                  .twitter(twitter)
                                                                  .linkedin(linkedin)
                                                                  .github(github)
                                                                  .build();
        EmailAddress compareValue = EmailAddressFixture.complete()
                                                       .email(email)
                                                       .twitter(twitter)
                                                       .linkedin("change linkedin")
                                                       .github(github)
                                                       .build();
        // when
        boolean result = originalValue.isEqual(compareValue);

        // then
        assertFalse(result);
    }

    @DisplayName("EmailAddressValue에서 Github 비교 시 다를경우 false를 반환한다.")
    @Test
    void isEqualNotGithubEqual() {
        // given
        EmailAddressValue originalValue = EmailAddressValueFixture.complete()
                                                                  .email(email)
                                                                  .twitter(twitter)
                                                                  .linkedin(linkedin)
                                                                  .github(github)
                                                                  .build();
        EmailAddress compareValue = EmailAddressFixture.complete()
                                                       .email(email)
                                                       .twitter(twitter)
                                                       .linkedin(linkedin)
                                                       .github("change github")
                                                       .build();
        // when
        boolean result = originalValue.isEqual(compareValue);

        // then
        assertFalse(result);
    }

    @DisplayName("EmailAddressValue에서 EmailAddress와 비교 시 같은경우 true를 반환한다.")
    @Test
    void isEqual() {
        // given
        EmailAddressValue originalValue = EmailAddressValueFixture.complete()
                                                                  .email(email)
                                                                  .twitter(twitter)
                                                                  .linkedin(linkedin)
                                                                  .github(github)
                                                                  .build();
        EmailAddress compareValue = EmailAddressFixture.complete()
                                                       .email(email)
                                                       .twitter(twitter)
                                                       .linkedin(linkedin)
                                                       .github(github)
                                                       .build();
        // when
        boolean result = originalValue.isEqual(compareValue);

        // then
        assertTrue(result);
    }
}