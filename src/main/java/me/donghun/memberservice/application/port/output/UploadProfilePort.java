package me.donghun.memberservice.application.port.output;

import org.springframework.web.multipart.MultipartFile;

public interface UploadProfilePort {
    void upload(String path, MultipartFile file);
}
