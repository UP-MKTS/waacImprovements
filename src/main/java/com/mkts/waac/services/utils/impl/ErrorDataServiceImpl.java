package com.mkts.waac.services.utils.impl;

import com.mkts.waac.services.utils.ErrorDataService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ErrorDataServiceImpl implements ErrorDataService {

    @Override
    public Map<String, String> fillErrorMap(BindingResult bindingResult) {
        Map<String, String> errorMsg = new HashMap<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            errorMsg.put(error.getField(), error.getDefaultMessage());
        }

        return errorMsg;
    }
}
