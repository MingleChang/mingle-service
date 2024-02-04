package mingle.chang.service.exception;

import mingle.chang.service.response.Response;
import mingle.chang.service.response.ResponseStatusEnum;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ResponseBody
    @ExceptionHandler
    public Response handleException(Exception e) {
        e.printStackTrace();
        return ExceptionUtils.defaultExceptionHandler(e);
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public Response handleBizException(ServiceException e) {
        return ExceptionUtils.serviceExceptionHandler(e);
    }


    @ResponseBody
    @ExceptionHandler(NoResourceFoundException.class)
    public Response handleNoResourceFoundException(NoResourceFoundException e) {
        return ExceptionUtils.serviceExceptionHandler(ResponseStatusEnum.NOT_FOUND);
    }
}
