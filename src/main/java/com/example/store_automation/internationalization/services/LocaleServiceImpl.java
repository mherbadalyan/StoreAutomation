package com.example.store_automation.internationalization.services;

import com.example.store_automation.internationalization.helper.LocaleResolver;
import org.springframework.stereotype.Service;
import org.springframework.context.MessageSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

@Service
public class LocaleServiceImpl implements LocaleService {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;

    public String getMessage(String code, HttpServletRequest request){
        return messageSource.getMessage(code, null, localeResolver.resolveLocale(request));
    }

}