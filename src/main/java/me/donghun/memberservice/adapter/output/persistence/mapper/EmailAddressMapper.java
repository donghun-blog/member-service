package me.donghun.memberservice.adapter.output.persistence.mapper;

import me.donghun.memberservice.adapter.output.persistence.entity.EmailAddressValue;
import me.donghun.memberservice.domain.model.EmailAddress;
import org.springframework.stereotype.Component;

@Component
public class EmailAddressMapper {

    public EmailAddress toDomainModel(EmailAddressValue emailAddressValue) {
        return EmailAddress.builder()
                           .email(emailAddressValue.getEmail())
                           .twitter(emailAddressValue.getTwitter())
                           .github(emailAddressValue.getGithub())
                           .linkedin(emailAddressValue.getLinkedin())
                           .build();
    }

    public EmailAddressValue toValueObject(EmailAddress emailAddress) {
        return EmailAddressValue.builder()
                                .email(emailAddress.getEmail())
                                .twitter(emailAddress.getTwitter())
                                .github(emailAddress.getGithub())
                                .linkedin(emailAddress.getLinkedin())
                                .build();
    }

}
