package aimyamaguchi.co.jp.aimspringsql.resume;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {

    Career findBycareerid(Long id);

}
