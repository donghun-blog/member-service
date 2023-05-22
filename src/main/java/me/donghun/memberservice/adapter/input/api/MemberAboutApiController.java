package me.donghun.memberservice.adapter.input.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.input.api.dto.BaseResponse;
import me.donghun.memberservice.adapter.input.api.dto.RegisterMemberAbout;
import me.donghun.memberservice.adapter.input.api.dto.MemberResponse;
import me.donghun.memberservice.adapter.input.api.dto.UpdateMemberAbout;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.application.port.input.MemberAboutCommandUseCase;
import me.donghun.memberservice.application.port.input.MemberAboutQueryUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members/about")
public class MemberAboutApiController {

    private final MemberAboutQueryUseCase memberAboutQueryUseCase;
    private final MemberAboutCommandUseCase memberAboutCommandUseCase;

    @GetMapping("/{memberId}")
    public BaseResponse<MemberResponse> getMemberAbout(@PathVariable Long memberId) {
        MemberDto memberDto = memberAboutQueryUseCase.getMemberInfo(memberId);
        return MemberResponse.success(memberDto);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<RegisterMemberAbout.Response> registerMemberAbout(
            @Valid @RequestPart(value = "about") RegisterMemberAbout.Request request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        Long memberId = memberAboutCommandUseCase.createAbout(request.toCommand(avatar));
        return RegisterMemberAbout.Response.success(memberId);
    }

    @PutMapping(value = "/{memberId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<UpdateMemberAbout.Response> updateMemberAbout(
            @PathVariable Long  memberId,
            @Valid @RequestPart(value = "about") UpdateMemberAbout.Request request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        memberAboutCommandUseCase.updateAbout(memberId, request.toCommand(avatar));
        return UpdateMemberAbout.Response.success();
    }
}
