package bd.course.work.services;

import bd.course.work.entities.Quest;
import bd.course.work.repositories.QuestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class QuestService {

    private final QuestRepository questRepository;

    public QuestService(QuestRepository questRepository) {
        this.questRepository = questRepository;
    }

    @Transactional
    public void completeQuest(Quest quest) {
        questRepository.updateQuestStatus(quest.getQuestId(), "Завершен");
    }

    public List<Quest> getAllQuests() {
        return questRepository.getAllQuests();
    }

    public Optional<Quest> getQuestById(Long questId) {
        return questRepository.findById(questId);
    }


}

