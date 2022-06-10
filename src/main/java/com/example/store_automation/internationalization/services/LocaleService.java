package com.example.store_automation.internationalization.services;


import javax.servlet.http.HttpServletRequest;

public interface LocaleService {
    String getMessage(String code, HttpServletRequest request);
}
