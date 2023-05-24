package me.donghun.memberservice.adapter.input.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.input.api.dto.BaseResponse;
import me.donghun.memberservice.adapter.input.api.dto.MemberResponse;
import me.donghun.memberservice.adapter.input.api.dto.RegisterMember;
import me.donghun.memberservice.adapter.input.api.dto.UpdateMember;
import me.donghun.memberservice.application.dto.MemberDto;
import me.donghun.memberservice.application.port.input.MemberCommandUseCase;
import me.donghun.memberservice.application.port.input.MemberQueryUseCase;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/members/about")
public class MemberAboutApiController {

    private final MemberCommandUseCase memberCommandUseCase;
    private final MemberQueryUseCase memberQueryUseCase;

//    @GetMapping("/{memberId}")
//    public BaseResponse<MemberResponse> getMemberAbout(@PathVariable Long memberId) {
//        MemberDto memberDto = memberQueryUseCase.getMemberById(memberId);
//        return MemberResponse.success(memberDto);
//    }

    @GetMapping("/{nickName}")
    public BaseResponse<MemberResponse> getMemberAbout(@PathVariable String nickName) {
        MemberDto memberDto = memberQueryUseCase.getMemberByNickname(nickName);
        return MemberResponse.success(memberDto);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public BaseResponse<RegisterMember.Response> registerMemberAbout(
            @Valid @RequestPart(value = "member") RegisterMember.Request request,
            @RequestPart(value = "profile", required = false) MultipartFile avatar) {
        Long memberId = memberCommandUseCase.create(request.toCommand(avatar));
        return RegisterMember.Response.success(memberId);
    }

//    @PutMapping(value = "/{memberId}")
//    public BaseResponse<UpdateMember.Response> updateMemberAbout(
//            @PathVariable Long  memberId,
//            @Valid @RequestBody UpdateMember.Request request) {
//        memberCommandUseCase.updateById(memberId, request.toCommand());
//        return UpdateMember.Response.success();
//    }

    @PutMapping(value = "/{nickName}")
    public BaseResponse<UpdateMember.Response> updateMemberAbout(
            @PathVariable String  nickName,
            @Valid @RequestBody UpdateMember.Request request) {
        memberCommandUseCase.updateByNickname(nickName, request.toCommand());
        return UpdateMember.Response.success();
    }
}
