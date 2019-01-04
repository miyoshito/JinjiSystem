package aimyamaguchi.co.jp.aimspringsql.curriculum.services;

import aimyamaguchi.co.jp.aimspringsql.curriculum.models.CurriculumModel;
import aimyamaguchi.co.jp.aimspringsql.curriculum.repositories.CurriculumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CvDeleteService {

    @Autowired
    private CurriculumRepository cr;

    public void softDeleteCV(Long id) {
        Optional<CurriculumModel> cv = cr.findById(id);
        if (cv.isPresent()) {
            cv.get().setDeleted(true);
            cr.save(cv.get());
        }
    }
}
