package mingle.chang.service.exception;

import mingle.chang.service.response.Response;
import mingle.chang.service.response.ResponseStatusEnum;

public class ExceptionUtils {
    public static Response allExceptionHandler(Throwable e) {
        if (e instanceof ServiceException) {
            return serviceExceptionHandler((ServiceException) e);
        }
        return defaultExceptionHandler(e);
    }

    public static Response serviceExceptionHandler(ServiceException e) {
        Response response = new Response();
        response.setStatus(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

    public static Response defaultExceptionHandler(Throwable e) {
        Response response = new Response();
        response.setStatus(ResponseStatusEnum.EXCEPTION.getStatus());
        response.setMessage(e.getMessage());
        return response;
    }

    public static Response serviceExceptionHandler(ResponseStatusEnum e) {
        Response response = new Response();
        response.setStatus(e.getStatus());
        response.setMessage(e.getMessage());
        return response;
    }
}
