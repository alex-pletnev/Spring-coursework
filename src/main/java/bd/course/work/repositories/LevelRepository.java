package bd.course.work.repositories;

import bd.course.work.entities.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {

    Optional<Level> findByValue(Long value);

}

