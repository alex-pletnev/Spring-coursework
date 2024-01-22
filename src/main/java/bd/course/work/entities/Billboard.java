package bd.course.work.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Billboard {
    private Long heroId;
    private Long questId;
    private boolean result;

    public Billboard(Long heroId, Long questId, boolean result) {
        this.heroId = heroId;
        this.questId = questId;
        this.result = result;
    }

    public Billboard(Long heroId, Long questId) {
        this.heroId = heroId;
        this.questId = questId;
    }

    public Billboard() {
    }
}
