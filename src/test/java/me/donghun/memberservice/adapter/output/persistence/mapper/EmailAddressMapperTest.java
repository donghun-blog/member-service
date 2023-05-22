package me.donghun.memberservice.adapter.output.persistence.mapper;

import me.donghun.memberservice.adapter.output.persistence.entity.EmailAddressValue;
import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.common.environment.AbstractDefaultTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EmailAddressMapperTest extends AbstractDefaultTest {

    @DisplayName("이메일 Value Object를 Domain Model로 변경 후 값을 확인한다.")
    @Test
    void toDomainModel() {
        // given
        EmailAddressMapper emailAddressMapper = new EmailAddressMapper();

        final String email = "test@gmail.com";
        final String twitter = "test@twitter.com";
        final String linkedin = "test@linkedin.com";
        final String github = "test@github.com";

        EmailAddressValue emailAddressValue = EmailAddressValue.builder()
                                                               .email(email)
                                                               .twitter(twitter)
                                                               .linkedin(linkedin)
                                                               .github(github)
                                                               .build();

        // when
        EmailAddress domainModel = emailAddressMapper.toDomainModel(emailAddressValue);

        // then
        assertThat(domainModel).extracting("email", "twitter", "github", "linkedin")
                               .contains(email, twitter, github, linkedin);
    }

    @DisplayName("이메일 Domain Model를 Value Object로 변경 후 값을 확인한다.")
    @Test
    void toValueObject() {
        // given
        EmailAddressMapper emailAddressMapper = new EmailAddressMapper();

        final String email = "test@gmail.com";
        final String twitter = "test@twitter.com";
        final String linkedin = "test@linkedin.com";
        final String github = "test@github.com";

        EmailAddress emailAddress = EmailAddress.builder()
                                                               .email(email)
                                                               .twitter(twitter)
                                                               .linkedin(linkedin)
                                                               .github(github)
                                                               .build();

        // when
        EmailAddressValue valueObject = emailAddressMapper.toValueObject(emailAddress);

        // then
        assertThat(valueObject).extracting("email", "twitter", "github", "linkedin")
                               .contains(email, twitter, github, linkedin);
    }
}