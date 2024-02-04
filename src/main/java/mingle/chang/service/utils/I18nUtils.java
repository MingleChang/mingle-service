package mingle.chang.service.utils;

import mingle.chang.service.config.SpringContextHolder;

import java.util.Locale;

public class I18nUtils {
    public static String getMessage(String code) {
        return SpringContextHolder.getI18nMessage(code, null, null,null);
    }
    public static String getMessage(String code, Locale locale) {
        return SpringContextHolder.getI18nMessage(code, null, null,locale);
    }
    public static String getMessage(String code, Object[] args) {
        return SpringContextHolder.getI18nMessage(code, args, null,null);
    }
    public static String getMessage(String code, Object[] args, Locale locale) {
        return SpringContextHolder.getI18nMessage(code, args, null,locale);
    }
    public static String getMessage(String code, String defaultMessage) {
        return SpringContextHolder.getI18nMessage(code, null, defaultMessage,null);
    }
    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return SpringContextHolder.getI18nMessage(code, null, defaultMessage,locale);
    }
    public static String getMessage(String code, Object[] args, String defaultMessage) {
        return SpringContextHolder.getI18nMessage(code, args, defaultMessage,null);
    }
    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        return SpringContextHolder.getI18nMessage(code, args, defaultMessage,locale);
    }
}
