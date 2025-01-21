package bd.course.work.controllers;

import bd.course.work.dto.input.PriorityInputDTO;
import bd.course.work.dto.output.PriorityOutputDTO;
import bd.course.work.services.PriorityService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления приоритетами.
 */
@RestController
@RequestMapping("/priorities")
public class PriorityController {

    private static final Logger LOGGER = LogManager.getLogger(PriorityController.class);

    private final PriorityService priorityService;

    public PriorityController(PriorityService priorityService) {
        this.priorityService = priorityService;
    }

    /**
     * Создание нового приоритета.
     *
     * @param priorityInputDTO DTO с данными нового приоритета.
     * @return Созданный приоритет в виде DTO.
     */
    @PostMapping
    public ResponseEntity<PriorityOutputDTO> createPriority(@Valid @RequestBody PriorityInputDTO priorityInputDTO) {
        LOGGER.info("Request to create priority: {}", priorityInputDTO.getName());
        PriorityOutputDTO createdPriority = priorityService.createPriority(priorityInputDTO);
        LOGGER.info("Priority created with ID: {}", createdPriority.getId());
        return ResponseEntity.ok(createdPriority);
    }

    /**
     * Получение всех приоритетов.
     *
     * @return Список всех приоритетов в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<PriorityOutputDTO>> getAllPriorities() {
        LOGGER.info("Request to fetch all priorities");
        List<PriorityOutputDTO> priorities = priorityService.getAllPriorities();
        LOGGER.info("Fetched {} priorities", priorities.size());
        return ResponseEntity.ok(priorities);
    }

    /**
     * Получение приоритета по идентификатору.
     *
     * @param id Идентификатор приоритета.
     * @return Найденный приоритет в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PriorityOutputDTO> getPriorityById(@PathVariable Long id) {
        LOGGER.info("Request to fetch priority by ID: {}", id);
        PriorityOutputDTO priority = priorityService.getPriorityById(id);
        LOGGER.info("Priority found: {}", priority.getName());
        return ResponseEntity.ok(priority);
    }

    /**
     * Обновление приоритета.
     *
     * @param id               Идентификатор приоритета.
     * @param priorityInputDTO DTO с обновлёнными данными.
     * @return Обновлённый приоритет в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PriorityOutputDTO> updatePriority(@PathVariable Long id, @Valid @RequestBody PriorityInputDTO priorityInputDTO) {
        LOGGER.info("Request to update priority with ID: {}", id);
        PriorityOutputDTO updatedPriority = priorityService.updatePriority(id, priorityInputDTO);
        LOGGER.info("Priority updated with ID: {}", updatedPriority.getId());
        return ResponseEntity.ok(updatedPriority);
    }

    /**
     * Удаление приоритета.
     *
     * @param id Идентификатор приоритета.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePriority(@PathVariable Long id) {
        LOGGER.info("Request to delete priority with ID: {}", id);
        priorityService.deletePriority(id);
        LOGGER.info("Priority deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}