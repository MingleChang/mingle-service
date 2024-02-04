package mingle.chang.service.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface ToolService {
    Map<String, Object> ip(HttpServletRequest request, String ip);
}
