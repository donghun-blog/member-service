package me.donghun.memberservice.domain.model;


import me.donghun.memberservice.domain.service.ExtensionDomainService;

import java.util.EnumSet;

public enum ProfileSupportType {
    JPG,
    JPEG,
    PNG,
    GIF
    ;

    public static boolean isSupport(String profile) {
        String extension = ExtensionDomainService.getExtension(profile);
        return EnumSet.allOf(ProfileSupportType.class)
                      .stream()
                      .anyMatch(e -> e.name().equals(extension.toUpperCase()));
    }

}
