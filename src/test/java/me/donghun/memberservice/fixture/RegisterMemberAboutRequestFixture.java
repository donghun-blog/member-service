package me.donghun.memberservice.fixture;

import me.donghun.memberservice.adapter.input.api.dto.RegisterMemberAbout;
import me.donghun.memberservice.domain.model.OccupationType;

public class RegisterMemberAboutRequestFixture {
    public static RegisterMemberAbout.Request.RequestBuilder complete() {
        return RegisterMemberAbout.Request
                .builder()
                .name("name")
                .occupation(OccupationType.BACKEND_ENGINEER.name())
                .company("company")
                .email("email@test.com")
                .twitter("twitter@test.com")
                .linkedin("linkedin@test.com")
                .github("github@test.com")
                .introduce("introduce")
                ;

    }
}
