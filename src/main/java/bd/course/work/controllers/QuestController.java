package bd.course.work.controllers;

import bd.course.work.dto.input.QuestInputDTO;
import bd.course.work.dto.output.QuestOutputDTO;
import bd.course.work.services.QuestService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления квестами.
 */
@RestController
@RequestMapping("/quests")
public class QuestController {

    private static final Logger LOGGER = LogManager.getLogger(QuestController.class);

    private final QuestService questService;

    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    /**
     * Создание нового квеста.
     *
     * @param questInputDTO DTO с данными нового квеста.
     * @return Созданный квест в виде DTO.
     */
    @PostMapping
    public ResponseEntity<QuestOutputDTO> createQuest(@Valid @RequestBody QuestInputDTO questInputDTO) {
        LOGGER.info("Request to create quest: {}", questInputDTO.getTitle());
        QuestOutputDTO createdQuest = questService.createQuest(questInputDTO);
        LOGGER.info("Quest created with ID: {}", createdQuest.getId());
        return ResponseEntity.ok(createdQuest);
    }

    /**
     * Получение всех квестов.
     *
     * @return Список всех квестов в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<QuestOutputDTO>> getAllQuests() {
        LOGGER.info("Request to fetch all quests");
        List<QuestOutputDTO> quests = questService.getAllQuests();
        LOGGER.info("Fetched {} quests", quests.size());
        return ResponseEntity.ok(quests);
    }

    /**
     * Получение квеста по идентификатору.
     *
     * @param id Идентификатор квеста.
     * @return Найденный квест в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<QuestOutputDTO> getQuestById(@PathVariable Long id) {
        LOGGER.info("Request to fetch quest by ID: {}", id);
        QuestOutputDTO quest = questService.getQuestById(id);
        LOGGER.info("Quest found: {}", quest.getTitle());
        return ResponseEntity.ok(quest);
    }

    /**
     * Обновление квеста.
     *
     * @param id            Идентификатор квеста.
     * @param questInputDTO DTO с обновлёнными данными.
     * @return Обновлённый квест в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<QuestOutputDTO> updateQuest(@PathVariable Long id, @Valid @RequestBody QuestInputDTO questInputDTO) {
        LOGGER.info("Request to update quest with ID: {}", id);
        QuestOutputDTO updatedQuest = questService.updateQuest(id, questInputDTO);
        LOGGER.info("Quest updated with ID: {}", updatedQuest.getId());
        return ResponseEntity.ok(updatedQuest);
    }

    /**
     * Удаление квеста.
     *
     * @param id Идентификатор квеста.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuest(@PathVariable Long id) {
        LOGGER.info("Request to delete quest with ID: {}", id);
        questService.deleteQuest(id);
        LOGGER.info("Quest deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}