package bd.course.work.services;

import bd.course.work.dto.input.BillboardInputDTO;
import bd.course.work.dto.output.BillboardOutputDTO;
import bd.course.work.entities.Billboard;
import bd.course.work.entities.Hero;
import bd.course.work.entities.Level;
import bd.course.work.entities.Quest;
import bd.course.work.entities.enums.QuestStatus;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.BillboardRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления таблицей объявлений.
 */
@Service
public class BillboardService {

    private static final Logger LOGGER = LogManager.getLogger(BillboardService.class);

    private final BillboardRepository billboardRepository;
    private final QuestService questService;
    private final HeroService heroService;
    private final LevelService levelService;

    public BillboardService(BillboardRepository billboardRepository, QuestService questService, HeroService heroService, LevelService levelService) {
        this.billboardRepository = billboardRepository;
        this.questService = questService;
        this.heroService = heroService;
        this.levelService = levelService;
    }

    static BillboardOutputDTO mapToOutputDTO(Billboard billboard) {
        BillboardOutputDTO dto = new BillboardOutputDTO();
        dto.setId(billboard.getId());
        dto.setHero(HeroService.mapToOutputDTO(billboard.getHero()));
        dto.setQuest(QuestService.mapToOutputDTO(billboard.getQuest()));
        dto.setResult(billboard.isResult());
        dto.setAt(billboard.getAt().toString());
        return dto;
    }

    public BillboardOutputDTO createBillboard(BillboardInputDTO billboardInputDTO) {
        LOGGER.debug("Creating billboard for hero: {}", billboardInputDTO.getHero().getName());
        Billboard billboard = mapToEntity(billboardInputDTO);
        Billboard savedBillboard = billboardRepository.save(billboard);
        return mapToOutputDTO(savedBillboard);
    }

    public List<BillboardOutputDTO> getAllBillboards() {
        LOGGER.debug("Fetching all billboards");
        return billboardRepository.findAll().stream()
                .map(BillboardService::mapToOutputDTO)
                .toList();
    }

    public BillboardOutputDTO getBillboardById(Long id) {
        LOGGER.debug("Fetching billboard by ID: {}", id);
        Billboard billboard = billboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billboard not found with ID: " + id));
        return mapToOutputDTO(billboard);
    }

    public BillboardOutputDTO updateBillboard(Long id, BillboardInputDTO billboardInputDTO) {
        LOGGER.debug("Updating billboard with ID: {}", id);
        Billboard billboard = billboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billboard not found with ID: " + id));
        billboard.setHero(heroService.getHeroEntityById(billboardInputDTO.getHero().getId()));
        billboard.setQuest(questService.getQuestEntityById(billboardInputDTO.getQuest().getId()));
        billboard.setResult(billboardInputDTO.isResult());
        billboard.setAt(billboardInputDTO.getAt() != null ? Timestamp.valueOf(billboardInputDTO.getAt()) : new Timestamp(System.currentTimeMillis()));
        Billboard updatedBillboard = billboardRepository.save(billboard);
        return mapToOutputDTO(updatedBillboard);
    }

    public void deleteBillboard(Long id) {
        LOGGER.debug("Deleting billboard with ID: {}", id);
        Billboard billboard = billboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billboard not found with ID: " + id));
        billboardRepository.delete(billboard);
    }

    private Billboard mapToEntity(BillboardInputDTO dto) {
        Billboard billboard = new Billboard();
        billboard.setHero(heroService.getHeroEntityById(dto.getHero().getId()));
        billboard.setQuest(questService.getQuestEntityById(dto.getQuest().getId()));
        billboard.setResult(dto.isResult());
        billboard.setAt(dto.getAt() != null ? Timestamp.valueOf(dto.getAt()) : new Timestamp(System.currentTimeMillis()));
        return billboard;
    }

    public BillboardOutputDTO completeQuest(BillboardInputDTO billboardInputDTO) {
        LOGGER.debug("Completing quest for hero: {} and quest: {}", billboardInputDTO.getHero().getName(), billboardInputDTO.getQuest().getTitle());

        Hero hero = heroService.getHeroEntityById(billboardInputDTO.getHero().getId());
        Quest quest = questService.getQuestEntityById(billboardInputDTO.getQuest().getId());

        // Проверяем статус квеста
        if (quest.getStatus() == QuestStatus.COMPLETED) {
            throw new IllegalStateException("Quest is already completed.");
        }

        if (quest.getStatus() == QuestStatus.FAILED) {
            throw new IllegalStateException("Quest has already failed.");
        }

        if (quest.getStatus() == QuestStatus.IN_PROGRESS) {
            throw new IllegalStateException("Quest has already in progress.");
        }


        // Обновляем статус квеста
        quest.setStatus(QuestStatus.IN_PROGRESS);
        questService.updateQuestStatus(quest.getId(), quest.getStatus());

        // Обновляем возраст героя
        hero.setAge(hero.getAge() + 1);

        // Проверяем уровень героя
        Level heroLevel = hero.getLevel();
        Level questLevel = quest.getMinHeroLevel();

        // Вычитаем урон от квеста из здоровья героя
        int newHp = hero.getCurrentHp() - quest.getDamageToHero();
        if (newHp <= 0) {
            // Герой погиб, сбрасываем здоровье и уменьшаем уровень
            hero.setCurrentHp(heroLevel.getMaxHp());
            long newLevelValue = Math.max(1, heroLevel.getValue() - 1);
            Level newLevel = levelService.getLevelByValue(newLevelValue);
            hero.setLevel(newLevel);
        } else {
            hero.setCurrentHp(newHp);
        }

        // Проверяем, успешно ли завершён квест
        boolean questSucceeded = heroLevel.getValue() >= questLevel.getValue();

        if (questSucceeded) {
            // Успех: обновляем опыт героя
            int newXp = hero.getXp() + quest.getXp();
            hero.setXp(newXp);

            // Проверяем, достиг ли герой нового уровня
            while (newXp >= heroLevel.getXpToNextLevel()) {
                newXp -= heroLevel.getXpToNextLevel();
                long newLevelValue = heroLevel.getValue() + 1;
                heroLevel = levelService.getLevelByValue(newLevelValue);

                hero.setLevel(heroLevel);
                hero.setCurrentHp(heroLevel.getMaxHp());
            }
            hero.setXp(newXp);

            quest.setStatus(QuestStatus.COMPLETED);
        } else {
            // Неудача
            quest.setStatus(QuestStatus.FAILED);
        }

        // Сохраняем изменения в герое, квесте и добавляем запись в Billboard
        Hero updatedHero = heroService.updateHero(hero.getId(), hero);
        Quest updatedQuest = questService.updateQuest(quest.getId(), quest);

        Billboard billboard = new Billboard();
        billboard.setHero(updatedHero);
        billboard.setQuest(updatedQuest);
        billboard.setResult(questSucceeded);
        billboard.setAt(new Timestamp(System.currentTimeMillis()));
        billboardRepository.save(billboard);
        LOGGER.debug("Quest completed with result: {}", questSucceeded ? "SUCCESS" : "FAILURE");
        return mapToOutputDTO(billboard);
    }

    Billboard getBillboardEntityById(Long id) {
        LOGGER.debug("Fetching billboard by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return billboardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Billboard not found with ID: " + id));
    }


}