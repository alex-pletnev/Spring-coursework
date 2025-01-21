package bd.course.work.services;

import bd.course.work.dto.input.LevelInputDTO;
import bd.course.work.dto.output.LevelOutputDTO;
import bd.course.work.entities.Level;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.LevelRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления уровнями.
 */
@Service
public class LevelService {

    private static final Logger LOGGER = LogManager.getLogger(LevelService.class);

    private final LevelRepository levelRepository;

    public LevelService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    static LevelOutputDTO mapToOutputDTO(Level level) {
        LevelOutputDTO dto = new LevelOutputDTO();
        dto.setId(level.getId());
        dto.setValue(level.getValue());
        dto.setXpToNextLevel(level.getXpToNextLevel());
        dto.setMaxHp(level.getMaxHp());
        dto.setDamage(level.getDamage());
        dto.setMana(level.getMana());
        return dto;
    }

    public LevelOutputDTO createLevel(LevelInputDTO levelInputDTO) {
        LOGGER.debug("Creating level with value: {}", levelInputDTO.getValue());
        Level level = mapToEntity(levelInputDTO);
        Level savedLevel = levelRepository.save(level);
        return mapToOutputDTO(savedLevel);
    }

    public List<LevelOutputDTO> getAllLevels() {
        LOGGER.debug("Fetching all levels");
        return levelRepository.findAll().stream()
                .map(LevelService::mapToOutputDTO)
                .toList();
    }

    public LevelOutputDTO getLevelById(Long id) {
        LOGGER.debug("Fetching level by ID: {}", id);
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found with ID: " + id));
        return mapToOutputDTO(level);
    }

    public LevelOutputDTO updateLevel(Long id, LevelInputDTO levelInputDTO) {
        LOGGER.debug("Updating level with ID: {}", id);
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found with ID: " + id));
        level.setValue(levelInputDTO.getValue());
        level.setXpToNextLevel(levelInputDTO.getXpToNextLevel());
        level.setMaxHp(levelInputDTO.getMaxHp());
        level.setDamage(levelInputDTO.getDamage());
        level.setMana(levelInputDTO.getMana());
        Level updatedLevel = levelRepository.save(level);
        return mapToOutputDTO(updatedLevel);
    }

    public void deleteLevel(Long id) {
        LOGGER.debug("Deleting level with ID: {}", id);
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found with ID: " + id));
        levelRepository.delete(level);
    }

    Level getLevelByValue(Long value) {
        LOGGER.debug("Fetching level by value: {}", value);
        return levelRepository.findById(value)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found with value: " + value));
    }

    Level getLevelEntityById(Long id) {
        LOGGER.debug("Fetching level entity by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return levelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Level not found with ID: " + id));
    }

    private Level mapToEntity(LevelInputDTO levelInputDTO) {
        Level level = new Level();
        level.setValue(levelInputDTO.getValue());
        level.setXpToNextLevel(levelInputDTO.getXpToNextLevel());
        level.setMaxHp(levelInputDTO.getMaxHp());
        level.setDamage(levelInputDTO.getDamage());
        level.setMana(levelInputDTO.getMana());
        return level;
    }
}