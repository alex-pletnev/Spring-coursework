package bd.course.work.entities;

import lombok.Data;


@Data
public class Level {

    private Long levelId;
    private int xp;
    private int hp;
    private int damage;
    private int mana;

}