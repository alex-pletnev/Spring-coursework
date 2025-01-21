package bd.course.work.controllers;

import bd.course.work.dto.output.UserOutputDTO;
import bd.course.work.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для управления пользователями.
 */
@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    /**
     * Получение всех пользователей.
     *
     * @return Список всех пользователей в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<UserOutputDTO>> getAllUsers() {
        LOGGER.info("Request to fetch all users");
        List<UserOutputDTO> users = userService.getAllUsers();
        LOGGER.info("Fetched {} users", users.size());
        return ResponseEntity.ok(users);
    }

    /**
     * Получение пользователя по идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Найденный пользователь в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserOutputDTO> getUserById(@PathVariable Long id) {
        LOGGER.info("Request to fetch user by ID: {}", id);
        UserOutputDTO user = userService.getUserById(id);
        LOGGER.info("User found: {}", user.getUsername());
        return ResponseEntity.ok(user);
    }

}