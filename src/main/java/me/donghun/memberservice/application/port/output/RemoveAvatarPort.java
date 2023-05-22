package me.donghun.memberservice.application.port.output;

public interface RemoveAvatarPort {

    boolean isExistImage(String path);
    void removeImage(String path);
}
