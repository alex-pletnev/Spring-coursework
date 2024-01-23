package bd.course.work.repositories;

import bd.course.work.entities.Class;
import bd.course.work.entities.Quest;
import bd.course.work.util.RowMappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClassRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Class> getAllClass() {
        String sql = "SELECT * FROM class";
        return jdbcTemplate.query(sql, RowMappers.getClassRowMapper());
    }
}
