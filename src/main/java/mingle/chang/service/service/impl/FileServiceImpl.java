package mingle.chang.service.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import mingle.chang.service.dataobject.FileDO;
import mingle.chang.service.exception.ServiceException;
import mingle.chang.service.repository.FileRepository;
import mingle.chang.service.response.ResponseStatusEnum;
import mingle.chang.service.service.FileService;
import mingle.chang.service.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Value("${custom.oss.region}")
    private String region;
    @Value("${custom.oss.end-point}")
    private String endPoint;
    @Value("${custom.oss.access-key-id}")
    private String accessKeyId;
    @Value("${custom.oss.access-key-secret}")
    private String accessKeySecret;
    @Value("${custom.oss.bucket}")
    private String bucket;

    @Resource
    private FileRepository fileRepository;

    private OSS buildOssClient() {
        String endPoint = SecurityUtils.decrypt(this.endPoint);
        String accessKeyId = SecurityUtils.decrypt(this.accessKeyId);
        String accessKeySecret = SecurityUtils.decrypt(this.accessKeySecret);
        OSS oss = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        return oss;
    }

    @Override
    public Long upload(MultipartFile file) {
        try {
            String name = file.getOriginalFilename();
            String contentType = file.getContentType();
            Long size = file.getSize();
            LocalDate localDate = LocalDate.now();
            String uuid = UUID.randomUUID().toString().replace("-", "");
            String directory = localDate.toString();
            String filePath = directory + "/" + uuid;
            InputStream inputStream = file.getInputStream();

            OSS oss = buildOssClient();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentType(contentType);
            objectMetadata.setContentLength(size);
            String bucket = SecurityUtils.decrypt(this.bucket);
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucket, filePath, inputStream, objectMetadata);
            oss.putObject(putObjectRequest);
            oss.shutdown();
            FileDO fileDO = this.fileRepository.save(name, contentType, size, filePath);
            return fileDO.getId();
        }catch (Exception e) {
            ServiceException.throwBizException(ResponseStatusEnum.FILE_UPLOAD_FAILED);
        }
        return null;
    }

    @Override
    public void download(Long fileId, HttpServletResponse response) {
        FileDO fileDO = this.fileRepository.getById(fileId);
        if (Objects.isNull(fileDO)) {
            ServiceException.throwBizException(ResponseStatusEnum.FILE_NOT_EXIST);
        }
        try {
            String fileName = fileDO.getName();
            String contentType = fileDO.getContentType();
            ServletOutputStream outputStream = null;
            OSS oss = buildOssClient();
            String bucket = SecurityUtils.decrypt(this.bucket);
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucket, fileDO.getPath());
            OSSObject ossObject = oss.getObject(getObjectRequest);
            InputStream inputStream = ossObject.getObjectContent();
            response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "utf-8") + ";filename*=utf-8''" + URLEncoder.encode(fileName, "utf-8"));
            response.addHeader("Content-Type", contentType);
            outputStream = response.getOutputStream();
            int len;
            byte[] b = new byte[1024];
            while ((len = inputStream.read(b)) > 0) {
                outputStream.write(b, 0, len);
            }
            ossObject.close();
            oss.shutdown();
        }catch (Exception e) {
            ServiceException.throwBizException(ResponseStatusEnum.FILE_DOWNLOAD_FAILED);
        }
    }
}
