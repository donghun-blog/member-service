package me.donghun.memberservice.adapter.input.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import me.donghun.memberservice.adapter.input.api.validation.EnumValid;
import me.donghun.memberservice.adapter.input.api.validation.ValidUrl;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

public class UpdateMember {

    @Getter
    public static class Request {
        @NotBlank(message = "이름은 필수입니다.")
        private String name;
        private String engName;
        private String nickName;
        @EnumValid(target = OccupationType.class, message = "직업을 다시 확인해주세요.")
        private String occupation;
        private String company;
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
        @ValidUrl
        private String twitter;
        @ValidUrl
        private String linkedin;
        @ValidUrl
        private String github;
        @NotBlank(message = "자기 소개는 필수입니다.")
        private String introduce;

        public MemberUpdateCommand toCommand() {
            return new MemberUpdateCommand(
                    name,
                    engName,
                    nickName,
                    OccupationType.valueOf(occupation),
                    company,
                    email,
                    twitter,
                    linkedin,
                    github,
                    introduce
            );
        }
    }

    public record Response(Long memberId) {
        public static BaseResponse<UpdateMember.Response> success() {
            return BaseResponse.success();
        }
    }
}
