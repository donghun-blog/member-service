package me.donghun.memberservice.adapter.input.api;


import com.fasterxml.jackson.core.JsonProcessingException;
import me.donghun.memberservice.adapter.input.api.dto.RegisterMemberAbout;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.application.port.input.MemberAboutCommandUseCase;
import me.donghun.memberservice.application.port.input.MemberAboutQueryUseCase;
import me.donghun.memberservice.common.EmptyParameters;
import me.donghun.memberservice.common.environment.AbstractDefaultPresentationTest;
import me.donghun.memberservice.domain.model.OccupationType;
import me.donghun.memberservice.fixture.MemberDtoFixture;
import me.donghun.memberservice.fixture.MockMultipartFileFixture;
import me.donghun.memberservice.fixture.RegisterMemberAboutRequestFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;

import java.nio.charset.StandardCharsets;

import static me.donghun.memberservice.adapter.input.api.GlobalExceptionHandler.FIELD_ERROR_MESSAGE;
import static me.donghun.memberservice.adapter.input.api.dto.BaseResponse.Result.ResultStatus.FAIL;
import static me.donghun.memberservice.adapter.input.api.dto.BaseResponse.Result.ResultStatus.SUCCESS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberAboutApiController.class)
class MemberAboutApiControllerTest extends AbstractDefaultPresentationTest {

    @MockBean
    private MemberAboutQueryUseCase memberAboutQueryUseCase;
    @MockBean
    private MemberAboutCommandUseCase memberAboutCommandUseCase;

    private static final String BASE_URL = "/api/v1/members/about";

    @DisplayName("회원소개 생성 시 이름이 없는 경우 예외 응답을 반환한다.")
    @EmptyParameters
    void registerMemberAboutNameEmpty(String emptyName) throws Exception {
        // given
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .name(emptyName)
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.result.status").value(FAIL.getDescription()))
               .andExpect(jsonPath("$.result.message").value(FIELD_ERROR_MESSAGE))
               .andExpect(jsonPath("$.body.name[0]").value("이름은 필수입니다."))
        ;
    }

    @DisplayName("회원소개 생성 시 깃허브, 링크드인, 트위터 대해서 null이 넘어온 경우")
    @EmptyParameters
    void registerMemberAboutSiteEmpty(String empty) throws Exception {
        // given
        final Long memberId = 1L;
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .github(empty)
                                                                               .linkedin(empty)
                                                                               .twitter(empty)
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        given(memberAboutCommandUseCase.createAbout(any()))
                .willReturn(memberId);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result.status").value(SUCCESS.getDescription()))
               .andExpect(jsonPath("$.body.memberId").value(memberId))
        ;
    }

    @DisplayName("회원소개 생성 시 직업이 맞지 않는 경우 예외응답을 반환한다.")
    @EmptyParameters
    @ValueSource(strings = {"occupation"})
    void registerMemberAboutNotExistOccupationType(String occupation) throws Exception {
        // given
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .occupation(occupation)
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.result.status").value(FAIL.getDescription()))
               .andExpect(jsonPath("$.result.message").value(FIELD_ERROR_MESSAGE))
               .andExpect(jsonPath("$.body.occupation[0]").value("직업을 다시 확인해주세요."))
        ;
    }

    @DisplayName("회원소개 생성 시 직업이 맞는 경우 성공응답을 반환한다.")
    @ParameterizedTest
    @EnumSource(OccupationType.class)
    void registerMemberAboutExistOccupationType(OccupationType occupation) throws Exception {
        // given
        final Long memberId = 1L;
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .occupation(occupation.getName())
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        given(memberAboutCommandUseCase.createAbout(any()))
                .willReturn(memberId);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result.status").value(SUCCESS.getDescription()))
               .andExpect(jsonPath("$.body.memberId").value(memberId))
        ;
    }

    @DisplayName("회원소개 생성 시 소개가 없는 경우 예외응답을 반환한다.")
    @EmptyParameters
    void registerMemberAboutIntroduceEmpty(String emptyIntroduce) throws Exception {
        // given
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .introduce(emptyIntroduce)
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.result.status").value(FAIL.getDescription()))
               .andExpect(jsonPath("$.result.message").value(FIELD_ERROR_MESSAGE))
               .andExpect(jsonPath("$.body.introduce[0]").value("자기 소개는 필수입니다."))

        ;
    }

    @DisplayName("회원소개 생성 시 이메일 형식이 다른 경우 예외응답을 반환한다.")
    @ValueSource(strings = {"test@@", "123.naver"})
    @ParameterizedTest
    void registerMemberAboutEmailNotValid(String email) throws Exception {
        // given
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .email(email)
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.result.status").value(FAIL.getDescription()))
               .andExpect(jsonPath("$.result.message").value(FIELD_ERROR_MESSAGE))
               .andExpect(jsonPath("$.body.email[0]").value("이메일 형식이 아닙니다."))
        ;
    }

    @DisplayName("회원소개 생성 시 이메일이 없는 경우 예외응답을 반환한다.")
    @ParameterizedTest
    @EmptySource
    void registerMemberAboutEmailEmpty(String emptyEmail) throws Exception {
        // given
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .email(emptyEmail)
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isBadRequest())
               .andExpect(jsonPath("$.result.status").value(FAIL.getDescription()))
               .andExpect(jsonPath("$.result.message").value(FIELD_ERROR_MESSAGE))
               .andExpect(jsonPath("$.body.email[0]").value("이메일은 필수입니다."))
        ;
    }

    @DisplayName("회원소개 생성 시 요청 파라미터 유효성 검사가 통과된 후 성공응답을 반환한다.")
    @Test
    void registerMemberAbout() throws Exception {
        // given
        final Long memberId = 1L;
        RegisterMemberAbout.Request request = RegisterMemberAboutRequestFixture.complete()
                                                                               .github("https://github.com/donghun93")
                                                                               .build();
        MockMultipartFile mockMultipartFile = MockMultipartFileFixture.complete();
        MockMultipartFile mockAbout = getMockAbout(request);

        given(memberAboutCommandUseCase.createAbout(any()))
                .willReturn(memberId);

        // when & then
        mockMvc.perform(
                       multipart(BASE_URL)
                               .file(mockMultipartFile)
                               .file(mockAbout))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.body.memberId").value(memberId))
        ;
    }

    @DisplayName("회원소개 조회 시 성공응답으로 회원소개 정보를 반환한다.")
    @Test
    void getMemberAbout() throws Exception {
        // given
        MemberDto memberDto = MemberDtoFixture.complete()
                                              .build();
        given(memberAboutQueryUseCase.getMemberInfo(anyLong()))
                .willReturn(memberDto);

        // when & then
        mockMvc.perform(
                       get(BASE_URL + "/{memberId}", 1L))
               .andDo(print())
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.result.status").value(SUCCESS.getDescription()))
               .andExpect(jsonPath("$.body.memberId").value(memberDto.getId()))
               .andExpect(jsonPath("$.body.type").value(memberDto.getType()
                                                                 .name()))
               .andExpect(jsonPath("$.body.name").value(memberDto.getName()))
               .andExpect(jsonPath("$.body.avatar").value(memberDto.getAvatar()))
               .andExpect(jsonPath("$.body.occupation").value(memberDto.getOccupationType()
                                                                       .getDescription()))
               .andExpect(jsonPath("$.body.company").value(memberDto.getCompany()))
               .andExpect(jsonPath("$.body.email").value(memberDto.getEmailAddress()
                                                                  .getEmail()))
               .andExpect(jsonPath("$.body.twitter").value(memberDto.getEmailAddress()
                                                                    .getTwitter()))
               .andExpect(jsonPath("$.body.linkedin").value(memberDto.getEmailAddress()
                                                                     .getLinkedin()))
               .andExpect(jsonPath("$.body.github").value(memberDto.getEmailAddress()
                                                                   .getGithub()))
               .andExpect(jsonPath("$.body.introduce").value(memberDto.getIntroduce()))

        ;
    }


    private MockMultipartFile getMockAbout(RegisterMemberAbout.Request request) throws JsonProcessingException {
        return new MockMultipartFile("about", "json", APPLICATION_JSON_VALUE,
                objectMapper.writeValueAsString(request)
                            .getBytes(StandardCharsets.UTF_8));
    }
}