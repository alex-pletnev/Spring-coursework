package bd.course.work.controllers;

import bd.course.work.dto.input.UserInputDTO;
import bd.course.work.dto.output.UserOutputDTO;
import bd.course.work.entities.User;
import bd.course.work.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final AuthService authService;

    @PostMapping("/reg")
    public ResponseEntity<User> createUser(@RequestBody @Valid UserInputDTO userInputDTO) {
        LOGGER.info(userInputDTO);
        User newUser = authService.createUser(userInputDTO);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping
    public ResponseEntity<UserOutputDTO> authenticateUser(@RequestBody @Valid UserInputDTO userInputDTO) {
        return authService.authenticateUser(userInputDTO.getUsername(), userInputDTO.getPassword())
                .map(ResponseEntity::ok)
                .orElseThrow();
    }

}
