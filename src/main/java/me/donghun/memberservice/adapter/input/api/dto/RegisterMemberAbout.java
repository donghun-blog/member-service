package me.donghun.memberservice.adapter.input.api.dto;

import lombok.Getter;
import me.donghun.memberservice.application.dto.MemberCreateCommand;
import org.springframework.web.multipart.MultipartFile;

public class RegisterMemberAbout {

    @Getter
    public static class Request {
        private String name;
        private String occupationType;
        private String company;
        private String email;
        private String twitter;
        private String linkedin;
        private String github;
        private String introduce;

        public MemberCreateCommand toCommand(MultipartFile avatar) {
            return new MemberCreateCommand(name, avatar, occupationType, company, email, twitter, linkedin, github, introduce);
        }
    }


    public record Response(Long memberId) {
        public static BaseResponse<Response> success(Long memberId) {
            return BaseResponse.success(new Response(memberId));
        }
    }
}
