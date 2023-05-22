package me.donghun.memberservice.application.service;

import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.s3.config.S3Properties;
import me.donghun.memberservice.application.port.output.RemoveAvatarPort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static org.springframework.util.StringUtils.hasText;

@Service
@RequiredArgsConstructor
public class RemoveAvatarImageService {

    private final RemoveAvatarPort removeAvatarPort;
    private final S3Properties s3Properties;

    @Async("removeImageTaskExecutor")
    public void removeAsync(String relativePath) {
        String removePath = getRemoveUrl(relativePath);

        if (hasText(removePath) && removeAvatarPort.isExistImage(removePath)) {
            removeAvatarPort.removeImage(removePath);
        }
    }

    private String getRemoveUrl(String relativePath) {
        if (!Objects.isNull(relativePath)) {
            return s3Properties.getPrefix() + relativePath;
        }
        return null;
    }
}
