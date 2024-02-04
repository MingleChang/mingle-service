package mingle.chang.service.response;

import java.util.Objects;

public class Response<T> {
    private Boolean success;
    private String message;
    private Integer status;
    private T data;

    public static <T> Response<T> create(ResponseStatusEnum statusEnum) {
        Response response = new Response();
        response.setStatus(statusEnum.getStatus());
        response.setMessage(statusEnum.getMessage());
        return response;
    }

    public static <T> Response<T> success() {
        return Response.create(ResponseStatusEnum.SUCCESS);
    }

    public static <T> Response<T> success(T data) {
        Response response = Response.success();
        response.setData(data);
        return response;
    }

    public static <T> Response<T> error() {
        return Response.create(ResponseStatusEnum.ERROR);
    }

    public static <T> Response<T> error(String message) {
        Response response = Response.error();
        response.setMessage(message);
        return response;
    }

    public static <T> Response<T> error(Exception e) {
        Response response = Response.create(ResponseStatusEnum.EXCEPTION);
        response.setMessage(e.getMessage());
        return response;
    }

    public Boolean getSuccess() {
        return Objects.equals(ResponseStatusEnum.SUCCESS.getStatus(), this.status);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
