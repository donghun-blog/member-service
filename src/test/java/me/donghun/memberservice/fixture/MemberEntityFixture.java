package me.donghun.memberservice.fixture;

import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;

public class MemberEntityFixture {
        public static MemberEntity.MemberEntityBuilder complete() {
            return MemberEntity.builder()
                    .id(1L)
                    .type(MemberType.AUTHORS)
                    .name("name")
                    .avatar("avatar.png")
                    .occupationType(OccupationType.BACKEND_ENGINEER)
                    .company("company")
                    .emailAddress(EmailAddressValueFixture.complete().build())
                    .introduce("introduce")
                    ;
        }
    }