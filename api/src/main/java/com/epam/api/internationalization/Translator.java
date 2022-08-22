package com.epam.api.internationalization;

import java.util.Locale;
import lombok.AllArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

/**
 * @author <a href="https://github.com/NodirUmarov">Nodir Umarov</a> on 8/16/2022
 */
@Component
@AllArgsConstructor
public class Translator {

    private final ReloadableResourceBundleMessageSource resourceBundleMessageSource;

    public String getMessage(MessageCode messageCode) {
        Locale locale = LocaleContextHolder.getLocale();
        return resourceBundleMessageSource.getMessage(messageCode.getCode(), null, locale);
    }

}
