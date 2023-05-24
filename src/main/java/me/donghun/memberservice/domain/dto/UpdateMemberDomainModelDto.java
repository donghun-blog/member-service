package me.donghun.memberservice.domain.dto;

import lombok.Builder;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

public record UpdateMemberDomainModelDto(
        String name,
        String engName,
        String nickName,
        OccupationType occupation,
        String company,
        String email,
        String twitter,
        String linkedin,
        String github,
        String introduce
) {
    @Builder
    public UpdateMemberDomainModelDto {
    }
}
