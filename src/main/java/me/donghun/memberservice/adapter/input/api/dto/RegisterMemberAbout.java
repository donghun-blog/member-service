package me.donghun.memberservice.adapter.input.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.donghun.memberservice.adapter.input.api.validation.EnumValid;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

public class RegisterMemberAbout {

    @Getter
    @NoArgsConstructor
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

        @Builder
        private Request(String name, String occupation, String company, String email, String twitter, String linkedin, String github, String introduce) {
            this.name = name;
            this.occupation = occupation;
            this.company = company;
            this.email = email;
            this.twitter = twitter;
            this.linkedin = linkedin;
            this.github = github;
            this.introduce = introduce;
        }

        public MemberCreateCommand toCommand(MultipartFile avatar) {
            return new MemberCreateCommand(name, avatar, occupation, company, email, twitter, linkedin, github, introduce);
        }
    }


    public record Response(Long memberId) {
        public static BaseResponse<Response> success(Long memberId) {
            return BaseResponse.success(new Response(memberId));
        }
    }
}
