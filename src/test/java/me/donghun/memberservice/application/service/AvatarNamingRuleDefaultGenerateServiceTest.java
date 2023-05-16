package me.donghun.memberservice.application.service;

import me.donghun.memberservice.filters.DefaultTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AvatarNamingRuleDefaultGenerateServiceTest extends DefaultTest {

    @DisplayName("아바타 경로 기본 네이밍을 생성 후 확장자를 확인한다.")
    @Test
    void generate() {
        // given
        AvatarNamingRuleDefaultGenerateService generateService = new AvatarNamingRuleDefaultGenerateService();
        String fileName = "default";
        String extension = ".png";

        // when
        String result = generateService.generate(fileName + extension);

        // then
        assertThat(result)
                .isNotBlank()
                .contains(extension);
    }

}