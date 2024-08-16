package mingle.chang.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletResponse;
import mingle.chang.service.response.Response;
import mingle.chang.service.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "File")
@RestController
@RequestMapping("file")
public class FileController {
    @Resource
    private FileService fileService;

    @Operation(summary = "上传")
    @PostMapping("upload")
    public Response<Long> upload(@RequestParam("file") MultipartFile file) {
        Long result = fileService.upload(file);
        return Response.success(result);
    }

    @Operation(summary = "下载")
    @GetMapping("download")
    @PermitAll
    public void download(@RequestParam("fileId") Long fileId, HttpServletResponse response) {
        fileService.download(fileId, response);
    }
}
