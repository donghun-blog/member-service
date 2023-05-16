package me.donghun.memberservice.application.service;

import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.application.port.out.SaveMemberPort;
import me.donghun.memberservice.application.port.out.UploadAvatarPort;
import me.donghun.memberservice.filters.DefaultTest;
import me.donghun.memberservice.filters.EmptyParameters;
import me.donghun.memberservice.domain.model.Member;
import me.donghun.memberservice.fixture.service.MemberCreateCommandFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.any;

@ExtendWith(MockitoExtension.class)
class MemberCommandServiceTest extends DefaultTest {

    @Mock
    private SaveMemberPort saveMemberPort;
    @Mock
    private UploadAvatarPort uploadAvatarPort;
    @InjectMocks
    private MemberAboutCommandService memberCommandService;

    @DisplayName("프로필 업로드된 후 회원 도메인에 대해서 검증한다.")
    @EmptyParameters
    @ValueSource(strings = "path://")
    void createAbout(String avatarPath) {
        // given
        MemberCreateCommand command = MemberCreateCommandFixture.complete();

        ArgumentCaptor<Member> memberArgumentCaptor = ArgumentCaptor.forClass(Member.class);

        given(uploadAvatarPort.upload(any()))
                .willReturn(avatarPath);

        // when
        Long memberId = memberCommandService.createAbout(command);

        // then
        //verify(saveMemberPort, times(1)).save(any());
        then(saveMemberPort).should()
                            .save(memberArgumentCaptor.capture());
        assertThat(memberArgumentCaptor.getValue()
                                       .getAvatar()).isEqualTo(avatarPath);
    }


}