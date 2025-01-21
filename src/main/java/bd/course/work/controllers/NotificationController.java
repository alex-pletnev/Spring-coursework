package bd.course.work.controllers;

import bd.course.work.dto.input.NotificationInputDTO;
import bd.course.work.dto.output.NotificationOutputDTO;
import bd.course.work.services.NotificationService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер для управления уведомлениями.
 */
@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private static final Logger LOGGER = LogManager.getLogger(NotificationController.class);

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    /**
     * Создание нового уведомления.
     *
     * @param notificationInputDTO DTO с данными нового уведомления.
     * @return Созданное уведомление в виде DTO.
     */
    @PostMapping
    public ResponseEntity<NotificationOutputDTO> createNotification(@Valid @RequestBody NotificationInputDTO notificationInputDTO) {
        LOGGER.info("Request to create notification for user: {}", notificationInputDTO.getUser().getUsername());
        NotificationOutputDTO createdNotification = notificationService.createNotification(notificationInputDTO);
        LOGGER.info("Notification created with ID: {}", createdNotification.getId());
        return ResponseEntity.ok(createdNotification);
    }

    /**
     * Получение всех уведомлений.
     *
     * @return Список всех уведомлений в виде DTO.
     */
    @GetMapping
    public ResponseEntity<List<NotificationOutputDTO>> getAllNotifications() {
        LOGGER.info("Request to fetch all notifications");
        List<NotificationOutputDTO> notifications = notificationService.getAllNotifications();
        LOGGER.info("Fetched {} notifications", notifications.size());
        return ResponseEntity.ok(notifications);
    }

    /**
     * Получение уведомления по идентификатору.
     *
     * @param id Идентификатор уведомления.
     * @return Найденное уведомление в виде DTO.
     */
    @GetMapping("/{id}")
    public ResponseEntity<NotificationOutputDTO> getNotificationById(@PathVariable Long id) {
        LOGGER.info("Request to fetch notification by ID: {}", id);
        NotificationOutputDTO notification = notificationService.getNotificationById(id);
        LOGGER.info("Notification found with ID: {}", notification.getId());
        return ResponseEntity.ok(notification);
    }

    /**
     * Обновление уведомления.
     *
     * @param id                   Идентификатор уведомления.
     * @param notificationInputDTO DTO с обновлёнными данными.
     * @return Обновлённое уведомление в виде DTO.
     */
    @PutMapping("/{id}")
    public ResponseEntity<NotificationOutputDTO> updateNotification(@PathVariable Long id, @Valid @RequestBody NotificationInputDTO notificationInputDTO) {
        LOGGER.info("Request to update notification with ID: {}", id);
        NotificationOutputDTO updatedNotification = notificationService.updateNotification(id, notificationInputDTO);
        LOGGER.info("Notification updated with ID: {}", updatedNotification.getId());
        return ResponseEntity.ok(updatedNotification);
    }

    /**
     * Удаление уведомления.
     *
     * @param id Идентификатор уведомления.
     * @return HTTP 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        LOGGER.info("Request to delete notification with ID: {}", id);
        notificationService.deleteNotification(id);
        LOGGER.info("Notification deleted with ID: {}", id);
        return ResponseEntity.noContent().build();
    }
}