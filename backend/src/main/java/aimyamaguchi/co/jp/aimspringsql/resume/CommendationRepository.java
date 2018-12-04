package aimyamaguchi.co.jp.aimspringsql.resume;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommendationRepository extends JpaRepository<Commendation, Long> {

    Commendation findBycommendationid(Long id);
}
