package mingle.chang.service.response;

import mingle.chang.service.utils.I18nUtils;

public enum ResponseStatusEnum {
    SUCCESS(200, "SUCCESS", "成功"),
    ERROR(400, "ERROR", "错误"),
    NO_PERMISSION(401, "NO_PERMISSION", "没有权限"),
    TOKEN_ERROR(402, "TOKEN_ERROR", "认证错误"),
    NOT_FOUND(404, "NOT_FOUND", "接口不存在"),
    EXCEPTION(500, "EXCEPTION", "异常"),

    USERNAME_EXIST(4000, "USERNAME_EXIST", "用户已存在"),
    USER_NOT_EXIST(4001, "USER_NOT_EXIST", "用户不存在"),
    USERNAME_OR_PASSWORD_ERROR(4002, "USERNAME_OR_PASSWORD_ERROR", "账号或密码错误"),

    FILE_UPLOAD_FAILED(4100, "FILE_UPLOAD_FAILED", "文件上传失败"),
    FILE_DOWNLOAD_FAILED(4101, "FILE_DOWNLOAD_FAILED", "文件下载失败"),
    FILE_NOT_EXIST(4102, "FILE_NOT_EXIST", "文件不存在"),
    ;
    private Integer status;
    private String code;
    private String message;

    ResponseStatusEnum(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return I18nUtils.getMessage(this.code, this.message);
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
