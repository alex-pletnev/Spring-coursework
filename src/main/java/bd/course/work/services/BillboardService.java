package bd.course.work.services;

import bd.course.work.DTO.BillboardDTO;
import bd.course.work.entities.Billboard;
import bd.course.work.entities.Hero;
import bd.course.work.repositories.BillboardRepository;
import bd.course.work.repositories.HeroRepository;
import bd.course.work.repositories.LevelRepository;
import bd.course.work.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class BillboardService {

    private final BillboardRepository billboardRepository;
    private final QuestRepository questRepository;
    private final HeroRepository heroRepository;
    private final LevelRepository levelRepository;


    @Autowired
    public BillboardService(BillboardRepository billboardRepository,
                            QuestRepository questRepository,
                            HeroRepository heroRepository,
                            LevelRepository levelRepository) {
        this.billboardRepository = billboardRepository;
        this.questRepository = questRepository;
        this.heroRepository = heroRepository;
        this.levelRepository = levelRepository;
    }

    @Transactional
    public Optional<Hero> completeQuest(BillboardDTO billboardDTO) {
        Long heroId = billboardDTO.heroId();
        Long questId = billboardDTO.questId();
        billboardRepository.addBillboard(new Billboard(heroId, questId));
        var quest = questRepository.findById(questId).orElse(null);
        var hero = heroRepository.findById(heroId).orElse(null);
        if (Objects.isNull(quest) || Objects.isNull(hero)) {
            return Optional.empty();
        }
        var level = levelRepository.findById(hero.getLevelId()).orElse(null);
        hero.setAge(hero.getAge() + 1);
        assert level != null;
        billboardRepository.updateBillboard(new Billboard(heroId, questId, true));
        hero.setCurrentHp(hero.getCurrentHp() - quest.getDamageToHero());
        if (hero.getCurrentHp() == 0) {
            hero.setCurrentHp(level.getHp());
        }
        if (quest.getMinHeroLevelId() <= level.getLevelId()) {
            questRepository.updateQuestStatus(questId, "Завершен");
            hero.setXp(hero.getXp() + quest.getXp());
            heroRepository.updateHero(hero);
            return Optional.of(hero);

        } else {
            questRepository.updateQuestStatus(questId, "Провален");
            heroRepository.updateHero(hero);
            return Optional.of(hero);
        }
    }


}
