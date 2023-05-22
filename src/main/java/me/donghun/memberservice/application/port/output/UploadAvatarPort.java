package me.donghun.memberservice.application.port.output;

import org.springframework.web.multipart.MultipartFile;

public interface UploadAvatarPort {
    /**
     * 상대 경로를 반환한다.
     * @param avatar
     * @return
     */
    String upload(MultipartFile avatar);
}
