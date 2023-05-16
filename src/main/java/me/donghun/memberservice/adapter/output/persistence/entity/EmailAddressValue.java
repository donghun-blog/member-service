package me.donghun.memberservice.adapter.output.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    }
}