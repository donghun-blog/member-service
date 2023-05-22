package me.donghun.memberservice.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MemberType implements CodeEnum {
    AUTHORS("저자");

    private final String description;
    @Override
    public String getName() {
        return this.name();
    }
}
