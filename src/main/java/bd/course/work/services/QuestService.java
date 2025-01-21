package bd.course.work.services;

import bd.course.work.dto.input.QuestInputDTO;
import bd.course.work.dto.output.QuestOutputDTO;
import bd.course.work.entities.Quest;
import bd.course.work.entities.enums.QuestStatus;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.QuestRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления квестами.
 */
@Service
public class QuestService {

    private static final Logger LOGGER = LogManager.getLogger(QuestService.class);

    private final QuestRepository questRepository;
    private final LevelService levelService;
    private final PriorityService priorityService;
    private final TypeService typeService;

    public QuestService(QuestRepository questRepository, LevelService levelService, PriorityService priorityService, TypeService typeService) {
        this.questRepository = questRepository;
        this.levelService = levelService;
        this.priorityService = priorityService;
        this.typeService = typeService;
    }

    static QuestOutputDTO mapToOutputDTO(Quest quest) {
        QuestOutputDTO dto = new QuestOutputDTO();
        dto.setId(quest.getId());
        dto.setTitle(quest.getTitle());
        dto.setDescription(quest.getDescription());
        dto.setDueDate(quest.getDueDate().toString());
        dto.setStatus(quest.getStatus().name());
        dto.setXp(quest.getXp());
        dto.setDamageToHero(quest.getDamageToHero());
        dto.setMinHeroLevel(LevelService.mapToOutputDTO(quest.getMinHeroLevel()));
        dto.setPriority(PriorityService.mapToOutputDTO(quest.getPriority()));
        dto.setType(TypeService.mapToOutputDTO(quest.getType()));
        return dto;
    }

    public QuestOutputDTO createQuest(QuestInputDTO questInputDTO) {
        LOGGER.debug("Creating quest: {}", questInputDTO.getTitle());
        Quest quest = mapToEntity(questInputDTO);
        Quest savedQuest = questRepository.save(quest);
        return mapToOutputDTO(savedQuest);
    }

    public List<QuestOutputDTO> getAllQuests() {
        LOGGER.debug("Fetching all quests");
        return questRepository.findAll().stream()
                .map(QuestService::mapToOutputDTO)
                .toList();
    }

    public QuestOutputDTO getQuestById(Long id) {
        LOGGER.debug("Fetching quest by ID: {}", id);
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quest not found with ID: " + id));
        return mapToOutputDTO(quest);
    }

    public QuestOutputDTO updateQuest(Long id, QuestInputDTO questInputDTO) {
        LOGGER.debug("Updating quest with ID: {}", id);
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quest not found with ID: " + id));
        updateEntityFromDTO(quest, questInputDTO);
        Quest updatedQuest = questRepository.save(quest);
        return mapToOutputDTO(updatedQuest);
    }

    Quest updateQuest(Long id, Quest newQuest) {
        LOGGER.debug("Updating quest with ID: {}", id);
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quest not found with ID: " + id));

        newQuest.setId(quest.getId());
        return questRepository.save(newQuest);
    }

    public QuestOutputDTO updateQuestStatus(Long id, QuestStatus questStatus) {
        LOGGER.debug("Updating quest status with ID: {}", id);
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quest not found with ID: " + id));
        quest.setStatus(questStatus);
        Quest updatedQuest = questRepository.save(quest);
        return mapToOutputDTO(updatedQuest);
    }

    public void deleteQuest(Long id) {
        LOGGER.debug("Deleting quest with ID: {}", id);
        Quest quest = questRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quest not found with ID: " + id));
        questRepository.delete(quest);
    }

    private Quest mapToEntity(QuestInputDTO questInputDTO) {
        Quest quest = new Quest();
        quest.setTitle(questInputDTO.getTitle());
        quest.setDescription(questInputDTO.getDescription());
        quest.setDueDate(Timestamp.valueOf(questInputDTO.getDueDate()));
        quest.setXp(questInputDTO.getXp());
        quest.setDamageToHero(questInputDTO.getDamageToHero());
        quest.setMinHeroLevel(levelService.getLevelEntityById(questInputDTO.getMinHeroLevel().getId()));
        quest.setPriority(priorityService.getPriorityEntityById(questInputDTO.getPriority().getId()));
        quest.setType(typeService.getTypeEntityById(questInputDTO.getType().getId()));
        return quest;
    }

    private void updateEntityFromDTO(Quest quest, QuestInputDTO questInputDTO) {
        quest.setTitle(questInputDTO.getTitle());
        quest.setDescription(questInputDTO.getDescription());
        quest.setDueDate(Timestamp.valueOf(questInputDTO.getDueDate()));
        quest.setXp(questInputDTO.getXp());
        quest.setDamageToHero(questInputDTO.getDamageToHero());
        quest.setMinHeroLevel(levelService.getLevelEntityById(questInputDTO.getMinHeroLevel().getId()));
        quest.setPriority(priorityService.getPriorityEntityById(questInputDTO.getPriority().getId()));
        quest.setType(typeService.getTypeEntityById(questInputDTO.getType().getId()));
    }

    Quest getQuestEntityById(Long id) {
        LOGGER.debug("Fetching quest by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return questRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quest not found with ID: " + id));
    }


}