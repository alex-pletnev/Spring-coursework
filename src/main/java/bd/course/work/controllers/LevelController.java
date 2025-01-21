package bd.course.work.controllers;

import bd.course.work.dto.input.LevelInputDTO;
import bd.course.work.dto.output.LevelOutputDTO;
import bd.course.work.services.LevelService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления уровнями.
 */
@RestController
@RequestMapping("/levels")
public class LevelController {

    private static final Logger LOGGER = LogManager.getLogger(LevelController.class);

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    /**
     * Создание нового уровня.
     *
     * @param levelInputDTO DTO с данными нового уровня.
     * @return Созданный уровень в виде DTO.
     */
    @PostMapping
    public ResponseEntity<LevelOutputDTO> createLevel(@Valid @RequestBody LevelInputDTO levelInputDTO) {
        LOGGER.info("Request to create level with value: {}", levelInputDTO.getValue());
        LevelOutputDTO createdLevel = levelService.createLevel(levelInputDTO);
        LOGGER.info("Level created with ID: {}", createdLevel.getId());
        return ResponseEntity.ok(createdLevel);
    }

    /**
     * Получение всех уровней.
     *
     * @return Список всех уровней в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<LevelOutputDTO>> getAllLevels() {
        LOGGER.info("Request to fetch all levels");
        List<LevelOutputDTO> levels = levelService.getAllLevels();
        LOGGER.info("Fetched {} levels", levels.size());
        return ResponseEntity.ok(levels);
    }

    /**
     * Получение уровня по идентификатору.
     *
     * @param id Идентификатор уровня.
     * @return Найденный уровень в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LevelOutputDTO> getLevelById(@PathVariable Long id) {
        LOGGER.info("Request to fetch level by ID: {}", id);
        LevelOutputDTO level = levelService.getLevelById(id);
        LOGGER.info("Level found with ID: {}", level.getId());
        return ResponseEntity.ok(level);
    }

    /**
     * Обновление уровня.
     *
     * @param id            Идентификатор уровня.
     * @param levelInputDTO DTO с обновлёнными данными.
     * @return Обновлённый уровень в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<LevelOutputDTO> updateLevel(@PathVariable Long id, @Valid @RequestBody LevelInputDTO levelInputDTO) {
        LOGGER.info("Request to update level with ID: {}", id);
        LevelOutputDTO updatedLevel = levelService.updateLevel(id, levelInputDTO);
        LOGGER.info("Level updated with ID: {}", updatedLevel.getId());
        return ResponseEntity.ok(updatedLevel);
    }

    /**
     * Удаление уровня.
     *
     * @param id Идентификатор уровня.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        LOGGER.info("Request to delete level with ID: {}", id);
        levelService.deleteLevel(id);
        LOGGER.info("Level deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}