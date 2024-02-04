package mingle.chang.service.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;
import java.util.Objects;

public class SpringContextHolder {
    private static ApplicationContext context;

    public SpringContextHolder(ApplicationContext context) {
        SpringContextHolder.context = context;
    }

    public static Object getBean(String name) {
        return context.getBean(name);
    }
    public static <T> T getBean(Class<T> classType) {
        return context.getBean(classType);
    }
    public static <T> T getBean(String name, Class<T> classType) {
        return context.getBean(name, classType);
    }

    public static String getI18nMessage(String code,  Object[] args, String defaultMessage, Locale locale) {
        Locale messageLocale = locale;
        if (Objects.isNull(messageLocale)) {
            messageLocale = LocaleContextHolder.getLocale();
        }
        return context.getMessage(code, args, defaultMessage, messageLocale);
    }
}
