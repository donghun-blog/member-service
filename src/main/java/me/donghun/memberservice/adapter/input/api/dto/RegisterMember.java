package me.donghun.memberservice.adapter.input.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.donghun.memberservice.adapter.input.api.validation.EnumValid;
import me.donghun.memberservice.adapter.input.api.validation.ValidUrl;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import me.donghun.memberservice.domain.model.OccupationType;
import org.springframework.web.multipart.MultipartFile;

public class RegisterMember {

    @Getter
    @NoArgsConstructor
    public static class Request {

        @NotBlank(message = "이름은 필수입니다.")
        private String name;
        private String engName;
        @NotBlank(message = "닉네임은 필수입니다.")
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

        @Builder
        private Request(String name, String engName, String nickName, String occupation, String company, String email, String twitter, String linkedin, String github, String introduce) {
            this.name = name;
            this.engName = engName;
            this.nickName = nickName;
            this.occupation = occupation;
            this.company = company;
            this.email = email;
            this.twitter = twitter;
            this.linkedin = linkedin;
            this.github = github;
            this.introduce = introduce;
        }

        public MemberCreateCommand toCommand(MultipartFile profile) {
            return new MemberCreateCommand(name, engName, nickName, email, introduce, OccupationType.valueOf(occupation), profile, github, twitter, linkedin);
        }
    }


    public record Response(Long memberId) {
        public static BaseResponse<Response> success(Long memberId) {
            return BaseResponse.success(new Response(memberId));
        }
    }
}
