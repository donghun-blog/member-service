package me.donghun.memberservice.adapter.input.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import me.donghun.memberservice.adapter.input.api.validation.EnumValid;
import me.donghun.memberservice.application.dto.MemberUpdateCommand;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

public class UpdateMemberAbout {

    @Getter
    public static class Request {
        @NotBlank(message = "이름은 필수입니다.")
        private String name;
        @EnumValid(target = OccupationType.class, message = "직업을 다시 확인해주세요.")
        private String occupation;
        private String company;
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "이메일 형식이 아닙니다.")
        private String email;
        @Email(message = "이메일 형식이 아닙니다.")
        private String twitter;
        @Email(message = "이메일 형식이 아닙니다.")
        private String linkedin;
        @Email(message = "이메일 형식이 아닙니다.")
        private String github;
        @NotBlank(message = "자기 소개는 필수입니다.")
        private String introduce;

        public MemberUpdateCommand toCommand(MultipartFile avatar) {
            return new MemberUpdateCommand(name, avatar, occupation, company, email, twitter, linkedin, github, introduce);
        }
    }

    public record Response(Long memberId) {
        public static BaseResponse<UpdateMemberAbout.Response> success() {
            return BaseResponse.success();
        }
    }
}
