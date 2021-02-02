package com.mkts.waac.services.utils;

import org.springframework.validation.BindingResult;

import java.util.Map;

public interface ErrorDataService {
    Map<String, String> fillErrorMap(BindingResult bindingResult);
}
