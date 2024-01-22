package bd.course.work.services;

import bd.course.work.entities.Notification;
import bd.course.work.repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getAllNotificationsForUser(Long userId) {
        return notificationRepository.findAllNotificationsForUser(userId);
    }

    @Transactional
    public Notification readNotification(Notification notification) {
        notification.setRead(true);
        notificationRepository.updateNotification(notification);
        return notification;
    }

    @Transactional
    public Notification addNotification(Notification notification) {
        notificationRepository.addNotification(notification);
        return notification;
    }
}
