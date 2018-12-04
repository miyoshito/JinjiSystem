package aimyamaguchi.co.jp.aimspringsql.resume;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QualificationRepository extends JpaRepository<Qualification, Long> {

    Qualification findByqualificationid(Long id);
}
