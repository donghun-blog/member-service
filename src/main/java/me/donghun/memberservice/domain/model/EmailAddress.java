package me.donghun.memberservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.utils.CheckEmptyValueUtils;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_EMAIL_EMPTY;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class EmailAddress {
    private final String email;
    private final String twitter;
    private final String linkedin;
    private final String github;

    public static EmailAddress createEmailAddress(String email, String twitter, String linkedIn, String github) {
        return EmailAddress.builder()
                           .email(email)
                           .twitter(twitter)
                           .linkedin(linkedIn)
                           .github(github)
                           .build();
    }

    public static EmailAddress createEmailAddress(EmailAddress emailAddress) {
        return EmailAddress.createEmailAddress(
                emailAddress.getEmail(),
                emailAddress.getTwitter(),
                emailAddress.getLinkedin(),
                emailAddress.getGithub()
        );
    }

    @Builder
    private EmailAddress(String email, String twitter, String linkedin, String github) {
        this.email = email;
        this.twitter = CheckEmptyValueUtils.checkEmptyValue(twitter);
        this.linkedin = CheckEmptyValueUtils.checkEmptyValue(linkedin);
        this.github = CheckEmptyValueUtils.checkEmptyValue(github);

        validate();
    }

    public boolean isEquals(EmailAddress emailAddress) {
        return this.email.equals(emailAddress.email) &&
                this.twitter.equals(emailAddress.twitter) &&
                this.linkedin.equals(emailAddress.linkedin) &&
                this.github.equals(emailAddress.github);
    }

    private void validate() {
        if (!hasText(email)) throw new MemberException(MEMBER_EMAIL_EMPTY);
    }
}
