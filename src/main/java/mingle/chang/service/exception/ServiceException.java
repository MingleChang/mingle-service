package mingle.chang.service.exception;

import mingle.chang.service.response.Response;
import mingle.chang.service.response.ResponseStatusEnum;

public class ServiceException  extends RuntimeException{
    protected Integer code;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
        this.code = ResponseStatusEnum.EXCEPTION.getStatus();
    }
    public ServiceException(Exception e) {
        this(e.getMessage());
    }
    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }
    public ServiceException(Response response) {
        super(response.getMessage());
        this.code = response.getStatus();
    }
    public ServiceException(ResponseStatusEnum responseStatusEnum) {
        super(responseStatusEnum.getMessage());
        this.code = responseStatusEnum.getStatus();
    }

    public static void throwBizException(String message) {
        ServiceException exception = new ServiceException(message);
        throw exception;
    }
    public static void throwBizException(Exception exception) {
        ServiceException bizException = new ServiceException(exception);
        throw bizException;
    }
    public static void throwBizException(Integer code, String message) {
        ServiceException exception = new ServiceException(code, message);
        throw exception;
    }
    public static void throwBizException(Response response) {
        ServiceException exception = new ServiceException(response);
        throw exception;
    }
    public static void throwBizException(ResponseStatusEnum responseStatusEnum) {
        ServiceException exception = new ServiceException(responseStatusEnum);
        throw exception;
    }

    public static <T> T verifyResponse(Response<T> response) {
        if (!response.getSuccess()) {
            throwBizException(response);
        }
        return response.getData();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
