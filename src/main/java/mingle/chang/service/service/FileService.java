package mingle.chang.service.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    Long upload(MultipartFile file);
    void download(Long fileId, HttpServletResponse response);
}
