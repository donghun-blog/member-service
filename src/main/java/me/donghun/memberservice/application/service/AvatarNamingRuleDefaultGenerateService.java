package me.donghun.memberservice.application.service;

import me.donghun.memberservice.application.port.input.AvatarNamingRuleGenerateUseCase;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AvatarNamingRuleDefaultGenerateService implements AvatarNamingRuleGenerateUseCase {
    @Override
    public String generate(String fileName) {
        return String.format("%s.%s", UUID.randomUUID(), FilenameUtils.getExtension(fileName));
    }
}
