package me.donghun.memberservice.adapter.input.api.test;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.input.api.dto.BaseResponse;
import me.donghun.memberservice.domain.exception.MemberErrorCode;
import me.donghun.memberservice.domain.exception.MemberException;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/test/members")
public class TestApiController {

    private final TestMemberRepository testMemberRepository;

    @GetMapping("/{memberId}")
    public BaseResponse<TestMemberResponse> getMemberInfo(@PathVariable Long memberId) {
        TestMemberEntity member = findMember(memberId);
        TestMemberResponse memberDto = TestMemberResponse.builder()
                                                         .email(member.getEmail())
                                                         .nickName(member.getNickName())
                                                         .id(member.getId())
                                                         .build();
        return BaseResponse.success(memberDto);
    }

    @PostMapping
    public BaseResponse<Long> register(@RequestBody TestMemberRegisterDto testMemberRegisterDto) {
        TestMemberEntity testMemberEntity = TestMemberEntity.builder()
                                                            .email(testMemberRegisterDto.getEmail())
                                                            .password(testMemberRegisterDto.getPassword())
                                                            .nickName(testMemberRegisterDto.getNickName())
                                                            .build();
        testMemberRepository.save(testMemberEntity);
        return BaseResponse.success("회원을 저장하였습니다.", testMemberEntity.getId());
    }

    @DeleteMapping("{memberId}")
    public BaseResponse<Long> deleteMember(@PathVariable Long memberId) {

        TestMemberEntity testMemberEntity = findMember(memberId);

        testMemberRepository.deleteById(memberId);
        return BaseResponse.success("회원을 삭제하였습니다.", memberId);
    }

    private TestMemberEntity findMember(Long memberId) {
        return testMemberRepository.findById(memberId)
                                   .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }
}
