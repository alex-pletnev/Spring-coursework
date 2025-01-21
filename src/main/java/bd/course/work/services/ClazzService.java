package bd.course.work.services;

import bd.course.work.dto.input.ClazzInputDTO;
import bd.course.work.dto.output.ClazzOutputDTO;
import bd.course.work.entities.Clazz;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.ClazzRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления классами героев.
 */
@Service
public class ClazzService {

    private static final Logger LOGGER = LogManager.getLogger(ClazzService.class);

    private final ClazzRepository clazzRepository;

    public ClazzService(ClazzRepository clazzRepository) {
        this.clazzRepository = clazzRepository;
    }

    static ClazzOutputDTO mapToOutputDTO(Clazz clazz) {
        ClazzOutputDTO dto = new ClazzOutputDTO();
        dto.setId(clazz.getId());
        dto.setClassName(clazz.getClassName());
        dto.setAbility(clazz.getAbility());
        return dto;
    }

    public ClazzOutputDTO createClazz(ClazzInputDTO clazzInputDTO) {
        LOGGER.debug("Creating class: {}", clazzInputDTO.getClassName());
        Clazz clazz = mapToEntity(clazzInputDTO);
        Clazz savedClazz = clazzRepository.save(clazz);
        return mapToOutputDTO(savedClazz);
    }

    public List<ClazzOutputDTO> getAllClasses() {
        LOGGER.debug("Fetching all classes");
        return clazzRepository.findAll().stream()
                .map(ClazzService::mapToOutputDTO)
                .toList();
    }

    public ClazzOutputDTO getClazzById(Long id) {
        LOGGER.debug("Fetching class by ID: {}", id);
        Clazz clazz = clazzRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + id));
        return mapToOutputDTO(clazz);
    }

    public ClazzOutputDTO updateClazz(Long id, ClazzInputDTO clazzInputDTO) {
        LOGGER.debug("Updating class with ID: {}", id);
        Clazz clazz = clazzRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + id));
        clazz.setClassName(clazzInputDTO.getClassName());
        clazz.setAbility(clazzInputDTO.getAbility());
        Clazz updatedClazz = clazzRepository.save(clazz);
        return mapToOutputDTO(updatedClazz);
    }

    public void deleteClazz(Long id) {
        LOGGER.debug("Deleting class with ID: {}", id);
        Clazz clazz = clazzRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + id));
        clazzRepository.delete(clazz);
    }

    private Clazz mapToEntity(ClazzInputDTO clazzInputDTO) {
        Clazz clazz = new Clazz();
        clazz.setClassName(clazzInputDTO.getClassName());
        clazz.setAbility(clazzInputDTO.getAbility());
        return clazz;
    }

    Clazz getClazzEntityById(Long id) {
        LOGGER.debug("Fetching class by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return clazzRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + id));
    }
}