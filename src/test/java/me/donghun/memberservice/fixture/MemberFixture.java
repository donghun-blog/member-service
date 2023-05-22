package me.donghun.memberservice.fixture;

import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.domain.model.MemberType;
import me.donghun.memberservice.domain.model.OccupationType;

public class MemberFixture {
        public static Member.MemberBuilder complete() {
            return Member.builder()
                         .id(1L)
                         .type(MemberType.AUTHORS)
                         .name("name")
                         .avatar("path:avatar.png")
                         .occupationType(OccupationType.BACKEND_ENGINEER)
                         .company("company")
                         .emailAddress(EmailAddressFixture.complete()
                                                          .build())
                         .introduce("자개소개")
                    ;
        }
    }