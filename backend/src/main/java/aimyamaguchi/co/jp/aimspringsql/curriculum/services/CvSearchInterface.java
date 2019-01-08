package aimyamaguchi.co.jp.aimspringsql.curriculum.services;

import java.util.List;

public interface CvSearchInterface {

    public List<String> searchForCV(String id);
    public List<String> searchForCV(String id, String name);

}
