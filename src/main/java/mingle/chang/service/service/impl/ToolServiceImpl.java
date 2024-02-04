package mingle.chang.service.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import mingle.chang.service.exception.ServiceException;
import mingle.chang.service.response.ResponseStatusEnum;
import mingle.chang.service.service.ToolService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Locale;
import java.util.Map;

@Slf4j
@Service
public class ToolServiceImpl implements ToolService {
    private final String ipApi = "http://ip-api.com/json/";

    @Override
    public Map<String, Object> ip(HttpServletRequest request, String ip) {
        Locale locale = LocaleContextHolder.getLocale();
        String lang = locale.getLanguage() + "-" + locale.getCountry();
        String ipAddress = ip;
        if (StringUtils.isEmpty(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        String url = ipApi + ipAddress + "?lang=" + lang;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> map = restTemplate.getForObject(url, Map.class);
        String status = (String) map.getOrDefault("status", "");
        if (StringUtils.equals(status, "success")) {
            return map;
        }else {
            ServiceException.throwBizException(ResponseStatusEnum.EXCEPTION);
        }
        return null;
    }
}
