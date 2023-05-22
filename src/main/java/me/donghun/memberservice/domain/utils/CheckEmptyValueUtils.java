package me.donghun.memberservice.domain.utils;

import static org.springframework.util.StringUtils.hasText;

public class CheckEmptyValueUtils {
    public static String checkEmptyValue(String value) {
        return (hasText(value)) ? value : null;
    }
}
