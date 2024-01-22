package bd.course.work.controllers;

import bd.course.work.entities.Hero;
import bd.course.work.services.HeroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hero")
public class HeroController {

    private final HeroService heroService;

    @Autowired
    public HeroController(HeroService heroService) {
        this.heroService = heroService;
    }

    @PostMapping
    public ResponseEntity<Hero> createHero(@RequestBody Hero hero) {
        Hero createdHero = heroService.createHero(hero);
        return ResponseEntity.ok(createdHero);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteHeroByUserId(@PathVariable Long userId) {
        heroService.deleteHeroByUserId(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Hero> getHeroByUserId(@PathVariable Long userId) {
        return heroService.getHeroByUserId(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
