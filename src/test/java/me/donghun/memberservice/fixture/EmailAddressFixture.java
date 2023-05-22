package me.donghun.memberservice.fixture;

import me.donghun.memberservice.domain.model.EmailAddress;

public class EmailAddressFixture {
    public static EmailAddress.EmailAddressBuilder complete() {
        return EmailAddress.builder()
                           .email("test@gmail.com")
                           .linkedin("test@linkedin.com")
                           .github("test@github.com")
                           .twitter("test@twitter.com")
                ;
    }
}