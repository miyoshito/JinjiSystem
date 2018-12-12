package aimyamaguchi.co.jp.aimspringsql.util;

public class CustomValidators {

    public boolean isNullValidator(String value){
        return (value != null && !value.equals(""));
    }

    public boolean isNullValidator(Long value){
        return (value != null && !value.equals(""));
    }
}

