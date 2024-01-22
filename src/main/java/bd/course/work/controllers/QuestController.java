package bd.course.work.controllers;

import bd.course.work.entities.Quest;
import bd.course.work.services.QuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/quest")
public class QuestController {

    private final QuestService questService;

    @Autowired
    public QuestController(QuestService questService) {
        this.questService = questService;
    }

    @GetMapping("/{questId}")
    public ResponseEntity<Quest> getQuestById(@PathVariable Long questId) {
        return questService.getQuestById(questId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Quest>> getAllQuests() {
        var quests = questService.getAllQuests();
        return ResponseEntity.ok(quests);
    }


}
