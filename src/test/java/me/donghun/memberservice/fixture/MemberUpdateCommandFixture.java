package me.donghun.memberservice.fixture;

import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.domain.model.OccupationType;

public class MemberUpdateCommandFixture {

    public static MemberUpdateCommand.MemberUpdateCommandBuilder complete() {
        return MemberUpdateCommand.builder()
                .name("test_name")
                .occupationType(OccupationType.BACKEND_ENGINEER.name())
                .avatar(MockMultipartFileFixture.complete())
                .company("test1company")
                .email("test2@test.com")
                .twitter("test3@twitter.com")
                .linkedin("test4@linkedin.com")
                .github("test5@github.com")
                .introduce("안녕하세요12315")
                ;
    }
}
