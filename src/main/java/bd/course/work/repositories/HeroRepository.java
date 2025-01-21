package bd.course.work.repositories;

import bd.course.work.entities.Hero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HeroRepository extends JpaRepository<Hero, Long> {

    boolean existsHeroById(Long id);
}