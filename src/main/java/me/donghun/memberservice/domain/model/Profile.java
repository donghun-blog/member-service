package me.donghun.memberservice.domain.model;

import lombok.Builder;
import lombok.Getter;
import me.donghun.memberservice.domain.exception.MemberException;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.*;
import static me.donghun.memberservice.domain.model.ProfileExtensionType.getExtension;
import static org.springframework.util.StringUtils.hasText;

@Getter
public class Profile {
    private final String path;
    private final ProfileExtensionType profileExtensionType;

    @Builder
    private Profile(String path) {
        if (!hasText(path)) {
            throw new MemberException(MEMBER_AVATAR_EMPTY);
        }
        this.path = path;
        this.profileExtensionType = getExtension(path);
    }

    public boolean isPathEqual(String path) {
        return this.path.equals(path);
    }

    // 프로필 생성
    public static Profile createProfile(String path) {
        return Profile.builder()
                      .path(path)
                      .build();
    }
}
