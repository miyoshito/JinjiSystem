package aimyamaguchi.co.jp.aimspringsql.util;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CustomValidators {

    public boolean isNullValidator(String value){
        return (value != null && !value.equals(""));
    }

    public boolean isNullValidator(Long value){
        return (value != null && !value.equals(""));
    }

    public boolean isNullValidator(Integer value){
        return (value != null && !value.equals(""));
    }

    public boolean isNullValidator(LocalDate value){
        return (value != null && !value.equals(""));
    }
}

