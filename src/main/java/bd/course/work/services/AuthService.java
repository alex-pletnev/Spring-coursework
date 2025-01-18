package bd.course.work.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import bd.course.work.dto.input.UserInputDTO;
import bd.course.work.dto.output.UserOutputDTO;
import bd.course.work.entities.User;
import bd.course.work.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;


    @Transactional
    public User createUser(UserInputDTO userInputDTO) {
        User user = new User();
        user.setUsername(userInputDTO.getUsername());
        user.setPassword(userInputDTO.getPassword());
        user.setEmail(userInputDTO.getEmail());
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
        return userRepository.save(user);
    }

    public Optional<UserOutputDTO> authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && BCrypt.verifyer().verify(password.toCharArray(), userRepository.findByUsername(username).orElseThrow().getPassword().toCharArray()).verified) {
            UserOutputDTO userOutputDTO = new UserOutputDTO();
            User u = user.get();
            userOutputDTO.setId(u.getId());
            userOutputDTO.setUsername(u.getUsername());
            userOutputDTO.setEmail(u.getEmail());
            return Optional.of(userOutputDTO);
        }
        return Optional.empty();
    }
}
