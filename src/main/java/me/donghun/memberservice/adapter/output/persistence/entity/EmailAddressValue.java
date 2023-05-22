package me.donghun.memberservice.adapter.output.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.donghun.memberservice.domain.exception.MemberException;
import me.donghun.memberservice.domain.model.EmailAddress;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_EMAIL_EMPTY;
import static org.springframework.util.StringUtils.hasText;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class EmailAddressValue {
    private String email;
    private String twitter;
    private String linkedin;
    private String github;

    @Builder
    private EmailAddressValue(String email, String twitter, String linkedin, String github) {
        this.email = email;
        this.twitter = twitter;
        this.linkedin = linkedin;
        this.github = github;

        validate();
    }

    public boolean isEqual(EmailAddress emailAddress) {
        return this.email.equals(emailAddress.getEmail()) &&
                this.twitter.equals(emailAddress.getTwitter()) &&
                this.linkedin.equals(emailAddress.getLinkedin()) &&
                this.github.equals(emailAddress.getGithub());
    }

    public static EmailAddressValue createEmailAddressValue(EmailAddress emailAddress) {
        return EmailAddressValue.builder()
                                .email(emailAddress.getEmail())
                                .twitter(emailAddress.getTwitter())
                                .linkedin(emailAddress.getLinkedin())
                                .github(emailAddress.getGithub())
                                .build();
    }

    private void validate() {
        if (!hasText(this.email)) throw new MemberException(MEMBER_EMAIL_EMPTY);
    }
}
