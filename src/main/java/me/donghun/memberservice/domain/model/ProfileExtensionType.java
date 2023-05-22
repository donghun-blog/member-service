package me.donghun.memberservice.domain.model;

import me.donghun.memberservice.domain.exception.MemberErrorCode;
import me.donghun.memberservice.domain.exception.MemberException;

import java.util.EnumSet;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_AVATAR_EXTENSION_NOT_SUPPORT;

public enum ProfileExtensionType {

    JPG,
    JPEG,
    PNG
    ;

    public static boolean isSupport(String path) {
        String extension = path.substring(path.lastIndexOf(".") + 1)
                               .toUpperCase();

        return EnumSet.allOf(ProfileExtensionType.class)
                      .stream()
                      .anyMatch(e -> e.name().equals(extension.toUpperCase()));
    }
    public static ProfileExtensionType getExtension(String path) {
        try {
            String extension = path.substring(path.lastIndexOf(".") + 1)
                                   .toUpperCase();

            if (!ProfileExtensionType.isSupport(extension)) {
                throw new MemberException(MEMBER_AVATAR_EXTENSION_NOT_SUPPORT);
            }

            return ProfileExtensionType.valueOf(extension);
        } catch (IllegalArgumentException e) {
            throw new MemberException(MEMBER_AVATAR_EXTENSION_NOT_SUPPORT);
        }
    }
}
