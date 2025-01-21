package bd.course.work.services;

import bd.course.work.dto.input.TypeInputDTO;
import bd.course.work.dto.output.TypeOutputDTO;
import bd.course.work.entities.Type;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.TypeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервис для управления типами квестов.
 */
@Service
public class TypeService {

    private static final Logger LOGGER = LogManager.getLogger(TypeService.class);

    private final TypeRepository typeRepository;

    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    static TypeOutputDTO mapToOutputDTO(Type type) {
        TypeOutputDTO dto = new TypeOutputDTO();
        dto.setId(type.getId());
        dto.setDescription(type.getDescription());
        dto.setDifficultyFactor(type.getDifficultyFactor());
        return dto;
    }

    public TypeOutputDTO createType(TypeInputDTO typeInputDTO) {
        LOGGER.debug("Creating type: {}", typeInputDTO.getDescription());
        Type type = new Type();
        type.setDescription(typeInputDTO.getDescription());
        type.setDifficultyFactor(typeInputDTO.getDifficultyFactor());
        Type savedType = typeRepository.save(type);
        return mapToOutputDTO(savedType);
    }

    public List<TypeOutputDTO> getAllTypes() {
        LOGGER.debug("Fetching all types");
        return typeRepository.findAll().stream()
                .map(TypeService::mapToOutputDTO)
                .collect(Collectors.toList());
    }

    public TypeOutputDTO getTypeById(Long id) {
        LOGGER.debug("Fetching type by ID: {}", id);
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with ID: " + id));
        return mapToOutputDTO(type);
    }

    public TypeOutputDTO updateType(Long id, TypeInputDTO typeInputDTO) {
        LOGGER.debug("Updating type with ID: {}", id);
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with ID: " + id));
        type.setDescription(typeInputDTO.getDescription());
        type.setDifficultyFactor(typeInputDTO.getDifficultyFactor());
        Type updatedType = typeRepository.save(type);
        return mapToOutputDTO(updatedType);
    }

    public void deleteType(Long id) {
        LOGGER.debug("Deleting type with ID: {}", id);
        Type type = typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with ID: " + id));
        typeRepository.delete(type);
    }

    Type getTypeEntityById(Long id) {
        LOGGER.debug("Fetching type by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return typeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Type not found with ID: " + id));
    }
}