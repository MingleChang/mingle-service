package mingle.chang.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import mingle.chang.service.response.Response;
import mingle.chang.service.service.ToolService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Tag(name = "Tool")
@RestController
@RequestMapping("tool")
public class ToolController {
    @Resource
    private ToolService toolService;

    @Operation(summary = "ip查询")
    @GetMapping("ip")
    @PermitAll
    public Response<Map<String, Object>> ip(HttpServletRequest request, @RequestParam(value = "ip", required = false) String ip) {
        Map<String, Object> result = toolService.ip(request, ip);
        return Response.success(result);
    }
}
