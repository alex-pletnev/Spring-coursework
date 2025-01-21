package bd.course.work.controllers;

import bd.course.work.dto.input.BillboardInputDTO;
import bd.course.work.dto.output.BillboardOutputDTO;
import bd.course.work.services.BillboardService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления таблицей объявлений.
 */
@RestController
@RequestMapping("/billboards")
public class BillboardController {

    private static final Logger LOGGER = LogManager.getLogger(BillboardController.class);

    private final BillboardService billboardService;

    public BillboardController(BillboardService billboardService) {
        this.billboardService = billboardService;
    }

    /**
     * Создание новой записи.
     *
     * @param billboardInputDTO DTO с данными новой записи.
     * @return Созданная запись в виде DTO.
     */
    @PostMapping
    public ResponseEntity<BillboardOutputDTO> createBillboard(@Valid @RequestBody BillboardInputDTO billboardInputDTO) {
        LOGGER.info("Request to create billboard for hero: {}", billboardInputDTO.getHero().getName());
        BillboardOutputDTO createdBillboard = billboardService.createBillboard(billboardInputDTO);
        LOGGER.info("Billboard created with ID: {}", createdBillboard.getId());
        return ResponseEntity.ok(createdBillboard);
    }

    /**
     * Получение всех записей.
     *
     * @return Список всех записей в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<BillboardOutputDTO>> getAllBillboards() {
        LOGGER.info("Request to fetch all billboards");
        List<BillboardOutputDTO> billboards = billboardService.getAllBillboards();
        LOGGER.info("Fetched {} billboards", billboards.size());
        return ResponseEntity.ok(billboards);
    }

    /**
     * Получение записи по идентификатору.
     *
     * @param id Идентификатор записи.
     * @return Найденная запись в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<BillboardOutputDTO> getBillboardById(@PathVariable Long id) {
        LOGGER.info("Request to fetch billboard by ID: {}", id);
        BillboardOutputDTO billboard = billboardService.getBillboardById(id);
        LOGGER.info("Billboard found with ID: {}", billboard.getId());
        return ResponseEntity.ok(billboard);
    }

    /**
     * Обновление записи.
     *
     * @param id                Идентификатор записи.
     * @param billboardInputDTO DTO с обновлёнными данными.
     * @return Обновлённая запись в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<BillboardOutputDTO> updateBillboard(@PathVariable Long id, @Valid @RequestBody BillboardInputDTO billboardInputDTO) {
        LOGGER.info("Request to update billboard with ID: {}", id);
        BillboardOutputDTO updatedBillboard = billboardService.updateBillboard(id, billboardInputDTO);
        LOGGER.info("Billboard updated with ID: {}", updatedBillboard.getId());
        return ResponseEntity.ok(updatedBillboard);
    }

    /**
     * Удаление записи.
     *
     * @param id Идентификатор записи.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBillboard(@PathVariable Long id) {
        LOGGER.info("Request to delete billboard with ID: {}", id);
        billboardService.deleteBillboard(id);
        LOGGER.info("Billboard deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Метод для выполнения квеста героем.
     *
     * @param billboardInputDTO DTO с данными героя и квеста.
     * @return Обновлённые DTO героя и квеста после выполнения квеста.
     */
    @PostMapping("/complete-quest")
    public ResponseEntity<BillboardOutputDTO> completeQuest(@Valid @RequestBody BillboardInputDTO billboardInputDTO) {
        LOGGER.info("Request to complete quest for hero: {}", billboardInputDTO.getHero().getName());
        BillboardOutputDTO billboardOutputDTO = billboardService.completeQuest(billboardInputDTO);
        LOGGER.info("Quest completed by hero: {}", billboardInputDTO.getHero().getName());
        return ResponseEntity.ok(billboardOutputDTO);
    }
}