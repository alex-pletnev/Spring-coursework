package bd.course.work.services;

import bd.course.work.dto.input.NotificationInputDTO;
import bd.course.work.dto.output.NotificationOutputDTO;
import bd.course.work.entities.Notification;
import bd.course.work.exceptions.InvalidRequestException;
import bd.course.work.exceptions.ResourceNotFoundException;
import bd.course.work.repositories.NotificationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

/**
 * Сервис для управления уведомлениями.
 */
@Service
public class NotificationService {

    private static final Logger LOGGER = LogManager.getLogger(NotificationService.class);

    private final NotificationRepository notificationRepository;
    private final UserService userService;

    public NotificationService(NotificationRepository notificationRepository, UserService userService) {
        this.notificationRepository = notificationRepository;
        this.userService = userService;

    }

    static NotificationOutputDTO mapToOutputDTO(Notification notification) {
        NotificationOutputDTO dto = new NotificationOutputDTO();
        dto.setId(notification.getId());
        dto.setContent(notification.getContent());
        dto.setCreatedAt(notification.getCreatedAt().toString());
        dto.setRead(notification.isRead());
        dto.setUser(UserService.mapToOutputDTO(notification.getUser()));
        return dto;
    }

    public NotificationOutputDTO createNotification(NotificationInputDTO notificationInputDTO) {
        LOGGER.debug("Creating notification for user: {}", notificationInputDTO.getUser().getUsername());
        Notification notification = mapToEntity(notificationInputDTO);
        Notification savedNotification = notificationRepository.save(notification);
        return mapToOutputDTO(savedNotification);
    }

    public List<NotificationOutputDTO> getAllNotifications() {
        LOGGER.debug("Fetching all notifications");
        return notificationRepository.findAll().stream()
                .map(NotificationService::mapToOutputDTO)
                .toList();
    }

    public NotificationOutputDTO getNotificationById(Long id) {
        LOGGER.debug("Fetching notification by ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
        return mapToOutputDTO(notification);
    }

    public NotificationOutputDTO updateNotification(Long id, NotificationInputDTO notificationInputDTO) {
        LOGGER.debug("Updating notification with ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
        notification.setContent(notificationInputDTO.getContent());
        notification.setRead(notificationInputDTO.isRead());
        notification.setUser(userService.getUserEntityById(notificationInputDTO.getId()));
        Notification updatedNotification = notificationRepository.save(notification);
        return mapToOutputDTO(updatedNotification);
    }

    public void deleteNotification(Long id) {
        LOGGER.debug("Deleting notification with ID: {}", id);
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
        notificationRepository.delete(notification);
    }

    private Notification mapToEntity(NotificationInputDTO dto) {
        Notification notification = new Notification();
        notification.setContent(dto.getContent());
        notification.setRead(dto.isRead());
        notification.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        notification.setUser(userService.getUserEntityById(dto.getUser().getId()));
        return notification;
    }

    Notification getNotificationEntityById(Long id) {
        LOGGER.debug("Fetching notification by ID: {}", id);
        if (Objects.isNull(id)) {
            throw new InvalidRequestException("ID is required for this operation");
        }
        return notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with ID: " + id));
    }

}