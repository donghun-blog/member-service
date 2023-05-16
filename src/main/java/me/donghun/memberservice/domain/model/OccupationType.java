package me.donghun.memberservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OccupationType {
    BACKEND_ENGINEER ("백엔드 엔지니어");

    private final String description;
}
