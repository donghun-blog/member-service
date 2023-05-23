package me.donghun.memberservice.domain.service;

public class ExtensionDomainService {
    public static String getExtension(String path) {
        return path.substring(path.lastIndexOf(".") + 1)
                   .toUpperCase();
    }
}
