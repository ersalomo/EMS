package com.service.impl;


import com.service.ValidatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Arrays;
import java.util.Set;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    @Autowired
    private Validator validator;


    @Override
    public void validate(Object o, Class<?>... groups) {
        if (groups.length == 0) {
            int length = groups.length;
            groups = Arrays.copyOf(groups, length + 1);
            groups[length] = Default.class;
        }
        Set<ConstraintViolation<Object>> constraintViolation = validator.validate(o,groups);
        if(!constraintViolation.isEmpty()) {
            throw new ConstraintViolationException(constraintViolation);
        }
    }
}
