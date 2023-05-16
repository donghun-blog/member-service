package me.donghun.memberservice.adapter.output.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import me.donghun.memberservice.adapter.output.s3.config.S3Properties;
import me.donghun.memberservice.application.port.input.AvatarNamingRuleGenerateUseCase;
import me.donghun.memberservice.application.port.out.UploadAvatarPort;
import me.donghun.memberservice.domain.exception.MemberException;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static me.donghun.memberservice.domain.exception.MemberErrorCode.MEMBER_AVATAR_UPLOAD_FAIL;

@Component
@RequiredArgsConstructor
public class MemberAvatarS3Adapter implements UploadAvatarPort {

    private final AmazonS3Client amazonS3Client;
    private final S3Properties properties;
    private final AvatarNamingRuleGenerateUseCase namingRuleService;

    @Override
    public String upload(MultipartFile avatar) {
        try {
            ObjectMetadata metaData = createMetaData(avatar);
            try (InputStream inputStream = avatar.getInputStream()) {
                String relativePath = namingRuleService.generate(avatar.getOriginalFilename());
                String fullPath = properties.getPrefix() + relativePath;
                amazonS3Client.putObject(
                        new PutObjectRequest(properties.getBucket(), fullPath, inputStream, metaData)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
                return relativePath;
            } catch (IOException e) {
                throw new FileUploadException();
            }
        } catch (IOException e) {
            throw new MemberException(MEMBER_AVATAR_UPLOAD_FAIL);
        }
    }

    private ObjectMetadata createMetaData(MultipartFile multipartFile) throws IOException {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getInputStream().available());
        objectMetadata.setContentType("image/" + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        return objectMetadata;
    }
}
