package bd.course.work.util;

import bd.course.work.entities.Class;
import bd.course.work.entities.*;
import org.springframework.jdbc.core.RowMapper;


public class RowMappers {

    private RowMappers() {
    }

    public static RowMapper<User> getUserRowMapper() {
        return (rs, rowNum) -> {
            User user = new User();
            user.setUserId(rs.getLong("user_id"));
            user.setUsername(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            return user;
        };
    }

    public static RowMapper<Level> getLevelRowMapper() {
        return (rs, rowNum) -> {
            Level level = new Level();
            level.setLevelId(rs.getLong("level_id"));
            level.setXp(rs.getInt("xp"));
            level.setHp(rs.getInt("hp"));
            level.setDamage(rs.getInt("damage"));
            level.setMana(rs.getInt("mana"));
            return level;
        };
    }

    public static RowMapper<Hero> getHeroRowMapper() {
        return (rs, rowNum) -> {
            Hero hero = new Hero();
            hero.setHeroId(rs.getLong("hero_id"));
            hero.setName(rs.getString("name"));
            hero.setAge(rs.getInt("age"));
            hero.setLevelId(rs.getLong("level_id"));
            hero.setUserId(rs.getLong("user_id"));
            hero.setHeroClassId(rs.getLong("class_id"));
            hero.setXp(rs.getInt("xp"));
            hero.setCurrentHp(rs.getInt("current_hp"));
            return hero;
        };
    }

    public static RowMapper<Quest> getQuestsRowMapper() {
        return (rs, rowNum) -> {
            Quest quest = new Quest();
            quest.setQuestId(rs.getLong("quest_id"));
            quest.setTitle(rs.getString("title"));
            quest.setDescription(rs.getString("description"));
            quest.setDueDate(rs.getDate("due_date").toLocalDate());
            quest.setStatus(rs.getString("status"));
            quest.setXp(rs.getInt("xp"));
            quest.setMinHeroLevelId(rs.getLong("min_hero_level_id"));
            quest.setDamageToHero(rs.getInt("damage_to_hero"));
            quest.setPriorityId(rs.getLong("priorities_id"));
            quest.setTypeId(rs.getLong("type_id"));
            return quest;
        };
    }

    public static RowMapper<Type> getTypeRowMapper() {
        return (rs, rowNum) -> {
            Type type = new Type();
            type.setTypeId(rs.getLong("type_id"));
            type.setDescription(rs.getString("Description"));
            return type;
        };
    }

    public static RowMapper<Class> getClassRowMapper() {
        return (rs, rowNum) -> {
            Class heroClass = new Class();
            heroClass.setClassId(rs.getLong("class_id"));
            heroClass.setClassName(rs.getString("class_name"));
            heroClass.setAbility(rs.getString("ability"));
            return heroClass;
        };
    }

    public static RowMapper<Billboard> getBillboardRowMapper() {
        return (rs, rowNum) -> {
            Billboard billboard = new Billboard();
            billboard.setHeroId(rs.getLong("hero_id"));
            billboard.setQuestId(rs.getLong("quests_id"));
            billboard.setResult(rs.getBoolean("result"));
            return billboard;
        };
    }

    public static RowMapper<Priority> getPrioritiesRowMapper() {
        return (rs, rowNum) -> {
            Priority priority = new Priority();
            priority.setPriorityId(rs.getLong("priority_id"));
            priority.setName(rs.getString("name"));
            return priority;
        };
    }

    public static RowMapper<Comment> getCommentsRowMapper() {
        return (rs, rowNum) -> {
            Comment comment = new Comment();
            comment.setCommentId(rs.getLong("comment_id"));
            comment.setQuestId(rs.getLong("quest_id"));
            comment.setUserId(rs.getLong("user_id"));
            comment.setCommentText(rs.getString("comment_text"));
            comment.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
            return comment;
        };
    }

    public static RowMapper<Notification> getNotificationsRowMapper() {
        return (rs, rowNum) -> {
            Notification notification = new Notification();
            notification.setNotificationId(rs.getLong("notification_id"));
            notification.setUserId(rs.getLong("user_id"));
            notification.setMessage(rs.getString("message"));
            notification.setCreatedAt(rs.getTimestamp("created_at"));
            notification.setRead(rs.getBoolean("read"));
            return notification;
        };
    }
}
