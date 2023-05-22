package me.donghun.memberservice.fixture;

import me.donghun.memberservice.adapter.output.persistence.entity.EmailAddressValue;

public class EmailAddressValueFixture {
    public static EmailAddressValue.EmailAddressValueBuilder complete() {
        return EmailAddressValue.builder()
                                .email("test@gmail.com")
                                .twitter("twitter@twitter.com")
                                .linkedin("linkedin@linkedin.com")
                                .github("github@github.com")
                ;
    }
}