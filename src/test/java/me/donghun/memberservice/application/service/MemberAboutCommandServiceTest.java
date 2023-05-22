package me.donghun.memberservice.application.service;

import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.common.environment.AbstractMysqlTestContainer;
import me.donghun.memberservice.adapter.output.persistence.repository.MemberRepository;
import me.donghun.memberservice.adapter.output.persistence.entity.MemberEntity;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.port.output.UploadAvatarPort;
import me.donghun.memberservice.domain.model.OccupationType;
import me.donghun.memberservice.fixture.MemberCreateCommandFixture;
import me.donghun.memberservice.fixture.MemberEntityFixture;
import me.donghun.memberservice.fixture.MemberUpdateCommandFixture;
import me.donghun.memberservice.fixture.MockMultipartFileFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


class MemberAboutCommandServiceTest extends AbstractMysqlTestContainer {

    @MockBean
    private UploadAvatarPort uploadAvatarPort;
    @MockBean
    private RemoveAvatarImageService removeAvatarImageService;

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
        assertThat(findMemberEntity)
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

    @DisplayName("회원 소개 업데이트 후 회원 데이터 검증한다.")
    @Test
    void updateAbout() {
        // given
        String uploadAvatarPath = "path://test.png";
        MemberEntity memberEntity = MemberEntityFixture.complete()
                                                       .build();
        memberRepository.save(memberEntity);

        MemberUpdateCommand command = MemberUpdateCommandFixture.complete()
                                                              .build();
        given(uploadAvatarPort.upload(any()))
                .willReturn(uploadAvatarPath);
        // when
        memberCommandService.updateAbout(memberEntity.getId(), command);

        // then
        MemberEntity findMemberEntity = memberRepository.findById(memberEntity.getId())
                                                     .orElseThrow();

        assertThat(findMemberEntity)
                .extracting("id", "name", "avatar", "company", "introduce")
                .contains(memberEntity.getId(), command.name(), uploadAvatarPath, command.company(),
                        command.introduce());

        assertThat(findMemberEntity.getEmailAddress())
                .extracting("email", "twitter", "linkedin", "github")
                .contains(command.email(), command.twitter(), command.linkedin(), command.github());
    }
}