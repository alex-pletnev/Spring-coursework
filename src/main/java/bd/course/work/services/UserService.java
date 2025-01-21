package bd.course.work.services;

import bd.course.work.dto.output.UserOutputDTO;
import bd.course.work.entities.User;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления пользователями.
 */
@Service
public class UserService {

    private static final Logger LOGGER = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    static UserOutputDTO mapToOutputDTO(User user) {
        UserOutputDTO dto = new UserOutputDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public List<UserOutputDTO> getAllUsers() {
        LOGGER.debug("Fetching all users");
        return userRepository.findAll().stream()
                .map(UserService::mapToOutputDTO)
                .toList();
    }

    public UserOutputDTO getUserById(Long id) {
        LOGGER.debug("Fetching user by ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return mapToOutputDTO(user);
    }

    User getUserEntityById(Long id) {
        LOGGER.debug("Fetching user by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }
}