package me.donghun.memberservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OccupationType implements CodeEnum {
    BACKEND_ENGINEER ("백엔드 엔지니어");

    private final String description;

    @Override
    public String getName() {
        return this.name();
    }
}
