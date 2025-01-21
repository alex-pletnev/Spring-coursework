package bd.course.work.controllers;

import bd.course.work.dto.input.HeroInputDTO;
import bd.course.work.dto.output.HeroOutputDTO;
import bd.course.work.services.HeroService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления героями.
 */
@RestController
@RequestMapping("/heroes")
public class HeroController {

    private static final Logger LOGGER = LogManager.getLogger(HeroController.class);

    private final HeroService heroService;

    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    /**
     * Создание нового героя.
     *
     * @param heroInputDTO DTO с данными нового героя.
     * @return Созданный герой в виде DTO.
     */
    @PostMapping
    public ResponseEntity<HeroOutputDTO> createHero(@Valid @RequestBody HeroInputDTO heroInputDTO) {
        LOGGER.info("Request to create hero: {}", heroInputDTO.getName());
        HeroOutputDTO createdHero = heroService.createHero(heroInputDTO);
        LOGGER.info("Hero created with ID: {}", createdHero.getId());
        return ResponseEntity.ok(createdHero);
    }

    /**
     * Получение всех героев.
     *
     * @return Список всех героев в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<HeroOutputDTO>> getAllHeroes() {
        LOGGER.info("Request to fetch all heroes");
        List<HeroOutputDTO> heroes = heroService.getAllHeroes();
        LOGGER.info("Fetched {} heroes", heroes.size());
        return ResponseEntity.ok(heroes);
    }

    /**
     * Получение героя по идентификатору.
     *
     * @param id Идентификатор героя.
     * @return Найденный герой в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<HeroOutputDTO> getHeroById(@PathVariable Long id) {
        LOGGER.info("Request to fetch hero by ID: {}", id);
        HeroOutputDTO hero = heroService.getHeroById(id);
        LOGGER.info("Hero found with ID: {}", hero.getId());
        return ResponseEntity.ok(hero);
    }

    /**
     * Обновление героя.
     *
     * @param id           Идентификатор героя.
     * @param heroInputDTO DTO с обновлёнными данными.
     * @return Обновлённый герой в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<HeroOutputDTO> updateHero(@PathVariable Long id, @Valid @RequestBody HeroInputDTO heroInputDTO) {
        LOGGER.info("Request to update hero with ID: {}", id);
        HeroOutputDTO updatedHero = heroService.updateHero(id, heroInputDTO);
        LOGGER.info("Hero updated with ID: {}", updatedHero.getId());
        return ResponseEntity.ok(updatedHero);
    }

    /**
     * Удаление героя.
     *
     * @param id Идентификатор героя.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHero(@PathVariable Long id) {
        LOGGER.info("Request to delete hero with ID: {}", id);
        heroService.deleteHero(id);
        LOGGER.info("Hero deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}