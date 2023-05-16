package me.donghun.memberservice.application.service;

import me.donghun.memberservice.MysqlTestContainer;
import me.donghun.memberservice.adapter.out.persistence.command.MemberRepository;
import me.donghun.memberservice.adapter.out.persistence.entity.MemberEntity;
import me.donghun.memberservice.adapter.out.s3.config.S3Config;
import me.donghun.memberservice.adapter.out.s3.config.S3Properties;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.port.out.UploadAvatarPort;
import me.donghun.memberservice.domain.model.OccupationType;
import me.donghun.memberservice.fixture.service.MemberCreateCommandFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.BDDAssertions.tuple;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


class MemberCommandServiceIntegratedTest extends MysqlTestContainer {

    @MockBean
    private UploadAvatarPort uploadAvatarPort;


    @Autowired
    private MemberAboutCommandService memberCommandService;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원소개 생성이되면 생성된 회원에 대한 ID값이 반환된다.")
    @Test
    void createAbout() {
        // given
        String uploadAvatarPath = "path://test.png";
        MemberCreateCommand command = MemberCreateCommandFixture.complete();

        given(uploadAvatarPort.upload(any()))
                .willReturn(uploadAvatarPath);

        // when
        Long memberId = memberCommandService.createAbout(command);

        // then
        MemberEntity findMemberEntity = memberRepository.findById(memberId).orElseThrow();
        Assertions.assertThat(findMemberEntity)
                .extracting("id", "name", "avatar",
                        "occupationType", "company", "introduce")
                .containsExactly(
                        memberId,
                        command.name(),
                        uploadAvatarPath,
                        OccupationType.valueOf(command.occupationType()),
                        command.company(),
                        command.introduce()
                );
    }
}