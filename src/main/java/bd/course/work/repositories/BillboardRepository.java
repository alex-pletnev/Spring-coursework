package bd.course.work.repositories;

import bd.course.work.entities.Billboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BillboardRepository extends JpaRepository<Billboard, Long> {

}
