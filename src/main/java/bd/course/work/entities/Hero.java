package bd.course.work.entities;

import lombok.Data;

@Data
public class Hero {
    private Long heroId;
    private String name;
    private int age;
    private int currentHp;
    private Long levelId;
    private Long userId;
    private Long heroClassId;
    private int xp;

    public void setXp(int xp) {
        this.xp = xp;
        if (this.xp >= levelId * 100) {
            this.xp -= (int) (levelId * 100);
            levelId++;
        }
    }

    public void setCurrentHp(int currentHp) {
        this.currentHp = currentHp;
        if (this.currentHp < 0) {
            this.currentHp = 0;
            levelId--;
            if (levelId < 1) {
                levelId = 1L;
            }
        }
    }


}