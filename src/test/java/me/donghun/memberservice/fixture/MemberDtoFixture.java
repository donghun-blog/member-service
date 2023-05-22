package me.donghun.memberservice.fixture;

import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;

public class MemberDtoFixture {
        public static MemberDto.MemberDtoBuilder complete() {
            return MemberDto.builder()
                            .id(1L)
                            .type(MemberType.AUTHORS)
                            .name("name")
                            .avatar("avatar.png")
                            .occupationType(OccupationType.BACKEND_ENGINEER)
                            .company("company")
                            .emailAddress(EmailAddressFixture.complete().build())
                            .introduce("introduce")
                    ;
        }
    }