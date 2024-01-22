package bd.course.work.services;

import bd.course.work.entities.Hero;
import bd.course.work.repositories.HeroRepository;
import bd.course.work.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class HeroService {

    private final HeroRepository heroRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public HeroService(HeroRepository heroRepository, LevelRepository levelRepository) {
        this.heroRepository = heroRepository;
        this.levelRepository = levelRepository;
    }

    @Transactional
    public Hero createHero(Hero hero) {
//        hero.setCurrentHp(levelRepository.findById(0L).orElseThrow().getHp());
        return heroRepository.save(hero);
    }

    @Transactional
    public void deleteHeroByHeroId(Long heroId) {
        heroRepository.deleteById(heroId);
    }

    public Optional<Hero> getHeroByHeroId(Long heroId) {
        return heroRepository.findById(heroId);
    }

    @Transactional
    public void deleteHeroByUserId(Long userId) {
        heroRepository.deleteById(userId);
    }

    public Optional<Hero> getHeroByUserId(Long userId) {
        return heroRepository.findById(userId);
    }
}
