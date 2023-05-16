package me.donghun.memberservice.application.dto;

import me.donghun.memberservice.domain.model.EmailAddress;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

public record MemberCreateCommand(
        String name,
        MultipartFile avatar,
        String occupationType,
        String company,
        String email,
        String twitter,
        String linkedin,
        String github,
        String introduce
) {
    public Member toEntity(String avatarPath) {
        return Member.createMember(
                name,
                avatarPath,
                MemberType.AUTHORS,
                OccupationType.valueOf(occupationType),
                company,
                EmailAddress.createEmailAddress(email, twitter, linkedin, github),
                introduce
        );
    }
}
