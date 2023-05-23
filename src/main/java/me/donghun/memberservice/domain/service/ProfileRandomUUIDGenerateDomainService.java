package me.donghun.memberservice.domain.service;

import java.util.UUID;

public class ProfileRandomUUIDGenerateDomainService {
    public static String generate(String profile) {
        return String.format("%s.%s", UUID.randomUUID(), ExtensionDomainService.getExtension(profile));
    }
}
