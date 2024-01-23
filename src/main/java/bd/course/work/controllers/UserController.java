package bd.course.work.controllers;

import bd.course.work.dto.UserDTO;
import bd.course.work.entities.User;
import bd.course.work.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserDTO userDTO) {
        LOGGER.info(userDTO);
        User newUser = userService.createUser(userDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<User> authenticateUser(@RequestBody UserDTO userDTO) {
        return userService.authenticateUser(userDTO.username(), userDTO.password())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null));
    }

}
