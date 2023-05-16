package me.donghun.memberservice.adapter.in.api;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.in.api.dto.BaseResponse;
import me.donghun.memberservice.adapter.in.api.dto.RegisterMemberAbout;
import me.donghun.memberservice.adapter.in.api.dto.MemberResponse;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.application.port.in.MemberAboutCommandUseCase;
import me.donghun.memberservice.application.port.in.MemberAboutQueryUseCase;
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

    @PostMapping
    public BaseResponse<RegisterMemberAbout.Response> registerMemberAbout(
            @RequestPart(value = "about") RegisterMemberAbout.Request request,
            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {
        Long memberId = memberAboutCommandUseCase.createAbout(request.toCommand(avatar));
        return RegisterMemberAbout.Response.success(memberId);
    }
}
