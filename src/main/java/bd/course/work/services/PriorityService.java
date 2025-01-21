package bd.course.work.services;

import bd.course.work.dto.input.PriorityInputDTO;
import bd.course.work.dto.output.PriorityOutputDTO;
import bd.course.work.entities.Priority;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.PriorityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Сервис для управления приоритетами.
 */
@Service
public class PriorityService {

    private static final Logger LOGGER = LogManager.getLogger(PriorityService.class);

    private final PriorityRepository priorityRepository;

    public PriorityService(PriorityRepository priorityRepository) {
        this.priorityRepository = priorityRepository;
    }

    static PriorityOutputDTO mapToOutputDTO(Priority priority) {
        PriorityOutputDTO dto = new PriorityOutputDTO();
        dto.setId(priority.getId());
        dto.setPriority(priority.getPriority());
        dto.setName(priority.getName());
        return dto;
    }

    public PriorityOutputDTO createPriority(PriorityInputDTO priorityInputDTO) {
        LOGGER.debug("Creating priority: {}", priorityInputDTO.getName());
        Priority priority = mapToEntity(priorityInputDTO);
        Priority savedPriority = priorityRepository.save(priority);
        return mapToOutputDTO(savedPriority);
    }

    public List<PriorityOutputDTO> getAllPriorities() {
        LOGGER.debug("Fetching all priorities");
        return priorityRepository.findAll().stream()
                .map(PriorityService::mapToOutputDTO)
                .collect(Collectors.toList());
    }

    public PriorityOutputDTO getPriorityById(Long id) {
        LOGGER.debug("Fetching priority by ID: {}", id);
        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found with ID: " + id));
        return mapToOutputDTO(priority);
    }

    public PriorityOutputDTO updatePriority(Long id, PriorityInputDTO priorityInputDTO) {
        LOGGER.debug("Updating priority with ID: {}", id);
        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found with ID: " + id));
        priority.setPriority(priorityInputDTO.getPriority());
        priority.setName(priorityInputDTO.getName());
        Priority updatedPriority = priorityRepository.save(priority);
        return mapToOutputDTO(updatedPriority);
    }

    public void deletePriority(Long id) {
        LOGGER.debug("Deleting priority with ID: {}", id);
        Priority priority = priorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found with ID: " + id));
        priorityRepository.delete(priority);
    }

    private Priority mapToEntity(PriorityInputDTO priorityInputDTO) {
        Priority priority = new Priority();
        priority.setPriority(priorityInputDTO.getPriority());
        priority.setName(priorityInputDTO.getName());
        return priority;
    }

    Priority getPriorityEntityById(Long id) {
        LOGGER.debug("Fetching priority by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return priorityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Priority not found with ID: " + id));
    }
}