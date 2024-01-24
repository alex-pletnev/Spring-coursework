package bd.course.work.services;

import bd.course.work.entities.Level;
import bd.course.work.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    public Optional<Level> getById(Long levelId) {
        return levelRepository.findById(levelId);
    }
}
