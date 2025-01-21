package bd.course.work.controllers;

import bd.course.work.dto.input.TypeInputDTO;
import bd.course.work.dto.output.TypeOutputDTO;
import bd.course.work.services.TypeService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления типами квестов.
 */
@RestController
@RequestMapping("/types")
public class TypeController {

    private static final Logger LOGGER = LogManager.getLogger(TypeController.class);

    private final TypeService typeService;

    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    /**
     * Создание нового типа.
     *
     * @param typeInputDTO DTO с данными нового типа.
     * @return Созданный тип в виде DTO.
     */
    @PostMapping
    public ResponseEntity<TypeOutputDTO> createType(@Valid @RequestBody TypeInputDTO typeInputDTO) {
        LOGGER.info("Request to create type: {}", typeInputDTO.getDescription());
        TypeOutputDTO createdType = typeService.createType(typeInputDTO);
        LOGGER.info("Type created with ID: {}", createdType.getId());
        return ResponseEntity.ok(createdType);
    }

    /**
     * Получение всех типов.
     *
     * @return Список всех типов в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<TypeOutputDTO>> getAllTypes() {
        LOGGER.info("Request to fetch all types");
        List<TypeOutputDTO> types = typeService.getAllTypes();
        LOGGER.info("Fetched {} types", types.size());
        return ResponseEntity.ok(types);
    }

    /**
     * Получение типа по идентификатору.
     *
     * @param id Идентификатор типа.
     * @return Найденный тип в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TypeOutputDTO> getTypeById(@PathVariable Long id) {
        LOGGER.info("Request to fetch type by ID: {}", id);
        TypeOutputDTO type = typeService.getTypeById(id);
        LOGGER.info("Type found: {}", type.getDescription());
        return ResponseEntity.ok(type);
    }

    /**
     * Обновление типа.
     *
     * @param id           Идентификатор типа.
     * @param typeInputDTO DTO с обновлёнными данными.
     * @return Обновлённый тип в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<TypeOutputDTO> updateType(@PathVariable Long id, @Valid @RequestBody TypeInputDTO typeInputDTO) {
        LOGGER.info("Request to update type with ID: {}", id);
        TypeOutputDTO updatedType = typeService.updateType(id, typeInputDTO);
        LOGGER.info("Type updated with ID: {}", updatedType.getId());
        return ResponseEntity.ok(updatedType);
    }

    /**
     * Удаление типа.
     *
     * @param id Идентификатор типа.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteType(@PathVariable Long id) {
        LOGGER.info("Request to delete type with ID: {}", id);
        typeService.deleteType(id);
        LOGGER.info("Type deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}