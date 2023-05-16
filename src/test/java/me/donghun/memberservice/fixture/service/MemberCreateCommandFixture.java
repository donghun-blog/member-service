package me.donghun.memberservice.fixture.service;

import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.mock.web.MockMultipartFile;

public class MemberCreateCommandFixture {
    public static MemberCreateCommand complete() {
        return new MemberCreateCommand("name",
                completeMockMultipartFile(),
                OccupationType.BACKEND_ENGINEER.name(),
                "company",
                "email",
                "twitter",
                "linkedin",
                "github",
                "introduce");
    }

    private static MockMultipartFile completeMockMultipartFile() {
        return new MockMultipartFile("image",
                "test.png",
                "image/png",
                "test".getBytes());
    }
}
