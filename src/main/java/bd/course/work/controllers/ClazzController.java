package bd.course.work.controllers;

import bd.course.work.dto.input.ClazzInputDTO;
import bd.course.work.dto.output.ClazzOutputDTO;
import bd.course.work.services.ClazzService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления классами героев.
 */
@RestController
@RequestMapping("/classes")
public class ClazzController {

    private static final Logger LOGGER = LogManager.getLogger(ClazzController.class);

    private final ClazzService clazzService;

    public ClazzController(ClazzService clazzService) {
        this.clazzService = clazzService;
    }

    /**
     * Создание нового класса.
     *
     * @param clazzInputDTO DTO с данными нового класса.
     * @return Созданный класс в виде DTO.
     */
    @PostMapping
    public ResponseEntity<ClazzOutputDTO> createClazz(@Valid @RequestBody ClazzInputDTO clazzInputDTO) {
        LOGGER.info("Request to create class: {}", clazzInputDTO.getClassName());
        ClazzOutputDTO createdClazz = clazzService.createClazz(clazzInputDTO);
        LOGGER.info("Class created with ID: {}", createdClazz.getId());
        return ResponseEntity.ok(createdClazz);
    }

    /**
     * Получение всех классов.
     *
     * @return Список всех классов в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<ClazzOutputDTO>> getAllClasses() {
        LOGGER.info("Request to fetch all classes");
        List<ClazzOutputDTO> classes = clazzService.getAllClasses();
        LOGGER.info("Fetched {} classes", classes.size());
        return ResponseEntity.ok(classes);
    }

    /**
     * Получение класса по идентификатору.
     *
     * @param id Идентификатор класса.
     * @return Найденный класс в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ClazzOutputDTO> getClazzById(@PathVariable Long id) {
        LOGGER.info("Request to fetch class by ID: {}", id);
        ClazzOutputDTO clazz = clazzService.getClazzById(id);
        LOGGER.info("Class found with ID: {}", clazz.getId());
        return ResponseEntity.ok(clazz);
    }

    /**
     * Обновление класса.
     *
     * @param id            Идентификатор класса.
     * @param clazzInputDTO DTO с обновлёнными данными.
     * @return Обновлённый класс в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ClazzOutputDTO> updateClazz(@PathVariable Long id, @Valid @RequestBody ClazzInputDTO clazzInputDTO) {
        LOGGER.info("Request to update class with ID: {}", id);
        ClazzOutputDTO updatedClazz = clazzService.updateClazz(id, clazzInputDTO);
        LOGGER.info("Class updated with ID: {}", updatedClazz.getId());
        return ResponseEntity.ok(updatedClazz);
    }

    /**
     * Удаление класса.
     *
     * @param id Идентификатор класса.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClazz(@PathVariable Long id) {
        LOGGER.info("Request to delete class with ID: {}", id);
        clazzService.deleteClazz(id);
        LOGGER.info("Class deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}