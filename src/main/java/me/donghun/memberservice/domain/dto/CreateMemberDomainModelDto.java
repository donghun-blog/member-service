package me.donghun.memberservice.domain.dto;

import lombok.Builder;
import me.donghun.memberservice.domain.model.OccupationType;

import java.time.LocalDateTime;

public record CreateMemberDomainModelDto(
        String name,
        String profile,
        OccupationType occupation,
        String company,
        String email,
        String github,
        String twitter,
        String linkedin,
        String introduce
) {
    @Builder
    public CreateMemberDomainModelDto {
    }
}
