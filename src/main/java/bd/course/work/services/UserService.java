package bd.course.work.services;

import at.favre.lib.crypto.bcrypt.BCrypt;
import bd.course.work.dto.UserDTO;
import bd.course.work.entities.User;
import bd.course.work.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setUsername(userDTO.username());
        user.setPassword(userDTO.password());
        user.setEmail(user.getEmail());
        if (userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Username or email already exists");
        }
        user.setPassword(BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray()));
        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && BCrypt.verifyer().verify(password.toCharArray(), userRepository.findByUsername(username).orElseThrow().getPassword().toCharArray()).verified) {
            return user;
        }
        return Optional.empty();
    }
}
