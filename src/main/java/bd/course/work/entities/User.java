package bd.course.work.entities;

import lombok.Data;


@Data
public class User {
    private Long userId;
    private String userName;
    private String password;
    private String email;
}

