package bd.course.work.services;

import bd.course.work.dto.input.HeroInputDTO;
import bd.course.work.dto.output.HeroOutputDTO;
import bd.course.work.entities.Hero;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.HeroRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления героями.
 */
@Service
public class HeroService {

    private static final Logger LOGGER = LogManager.getLogger(HeroService.class);

    private final HeroRepository heroRepository;
    private final UserService userService;
    private final LevelService levelService;
    private final ClazzService clazzService;

    public HeroService(HeroRepository heroRepository, UserService userService, LevelService levelService, ClazzService clazzService) {
        this.heroRepository = heroRepository;
        this.userService = userService;
        this.levelService = levelService;
        this.clazzService = clazzService;
    }

    static HeroOutputDTO mapToOutputDTO(Hero hero) {
        HeroOutputDTO dto = new HeroOutputDTO();
        dto.setId(hero.getId());
        dto.setName(hero.getName());
        dto.setAge(hero.getAge());
        dto.setCurrentHp(hero.getCurrentHp());
        dto.setXp(hero.getXp());
        dto.setLevel(LevelService.mapToOutputDTO(hero.getLevel()));
        dto.setUser(UserService.mapToOutputDTO(hero.getUser()));
        dto.setClazz(ClazzService.mapToOutputDTO(hero.getClazz()));
        return dto;
    }

    public HeroOutputDTO createHero(HeroInputDTO heroInputDTO) {
        LOGGER.debug("Creating hero: {}", heroInputDTO.getName());
        Hero hero = mapToEntity(heroInputDTO);
        Hero savedHero = heroRepository.save(hero);
        return mapToOutputDTO(savedHero);
    }

    public List<HeroOutputDTO> getAllHeroes() {
        LOGGER.debug("Fetching all heroes");
        return heroRepository.findAll().stream()
                .map(HeroService::mapToOutputDTO)
                .toList();
    }

    public HeroOutputDTO getHeroById(Long id) {
        LOGGER.debug("Fetching hero by ID: {}", id);
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with ID: " + id));
        return mapToOutputDTO(hero);
    }

    public HeroOutputDTO updateHero(Long id, HeroInputDTO heroInputDTO) {
        LOGGER.debug("Updating hero with ID: {}", id);
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with ID: " + id));
        hero.setName(heroInputDTO.getName());
        hero.setAge(heroInputDTO.getAge());
        hero.setCurrentHp(heroInputDTO.getCurrentHp());
        hero.setXp(heroInputDTO.getXp());
        hero.setLevel(levelService.getLevelEntityById(heroInputDTO.getLevel().getId()));
        hero.setUser(userService.getUserEntityById(heroInputDTO.getUser().getId()));
        hero.setClazz(clazzService.getClazzEntityById(heroInputDTO.getClazz().getId()));
        Hero updatedHero = heroRepository.save(hero);
        return mapToOutputDTO(updatedHero);
    }

    Hero updateHero(Long id, Hero newHero) {
        LOGGER.debug("Updating hero with ID: {}", id);
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with ID: " + id));
        newHero.setId(hero.getId());
        return heroRepository.save(newHero);
    }

    public void deleteHero(Long id) {
        LOGGER.debug("Deleting hero with ID: {}", id);
        Hero hero = heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with ID: " + id));
        heroRepository.delete(hero);
    }

    private Hero mapToEntity(HeroInputDTO heroInputDTO) {
        Hero hero = new Hero();
        hero.setName(heroInputDTO.getName());
        hero.setAge(heroInputDTO.getAge());
        hero.setCurrentHp(heroInputDTO.getCurrentHp());
        hero.setXp(heroInputDTO.getXp());
        hero.setLevel(levelService.getLevelEntityById(heroInputDTO.getLevel().getId()));
        hero.setUser(userService.getUserEntityById(heroInputDTO.getUser().getId()));
        hero.setClazz(clazzService.getClazzEntityById(heroInputDTO.getClazz().getId()));
        return hero;
    }

    Hero getHeroEntityById(Long id) {
        LOGGER.debug("Fetching hero by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return heroRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hero not found with ID: " + id));
    }

}