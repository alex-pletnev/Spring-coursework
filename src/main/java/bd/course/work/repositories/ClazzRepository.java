package bd.course.work.repositories;

import bd.course.work.entities.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClazzRepository extends JpaRepository<Clazz, Long> {

}
