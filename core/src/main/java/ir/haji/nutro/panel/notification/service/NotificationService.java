package ir.haji.nutro.panel.notification.service;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.notification.entity.Notification;
import ir.haji.nutro.panel.notification.repo.NotificationRepo;
import ir.haji.nutro.panel.um.entity.Role;
import ir.haji.nutro.panel.um.entity.User;
import ir.haji.nutro.panel.um.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Saeed on 1/16/2018.
 */
@Service
public class NotificationService {
    Logger logger = LoggerFactory.getLogger(NotificationService.class);
    @Autowired
    private SubscriptionService subService;
    @Autowired
    private NotificationRepo notificationRepo;
    @Autowired
    private UserService userService;


/*
    @Scheduled(fixedDelay = 30_000)
    public void sendNotifications() {
        List<Notification> notifs = notificationRepo.findBySeen(false);
        for (Notification notif : notifs) {
            Boolean sent = subService.sendPushMessageToUser(new PushMessage(notif.getTitle())
                            .body(notif.getBody())
                            .url(notif.getLink())
                    , notif.getUserId()
            );
            if (sent)
                notif.setSeen(true);
            notificationRepo.save(notif);
        }
    }
*/

    public void newNotification(Long userId, String title, String body, String link) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setTitle(title);
        notification.setBody(body);
        notification.setLink(link);
        newNotification(notification);
    }

    public void newNotification(Notification notification) {
        if (notification.getTitle() == null || notification.getTitle().isEmpty()) {
            throw new BadRequestException("نوتیفیکیشن بدون عنوان نباید باشد");
        }
        if (notification.getBody() == null || notification.getBody().isEmpty()) {
            throw new BadRequestException("نوتیفیکیشن بدون متن نباید باشد");
        }
        if (notification.getUserId() == null) {
            throw new BadRequestException("کاربر نوتیف شده باید مشخص باشد");
        }
        notification.setCreateDate(new Date());
        notification.setId(null);
        notification.setSeen(false);
        notificationRepo.save(notification);
    }

    public PanelNotificationBuilder builder() {
        return new PanelNotificationBuilder();
    }

    public class PanelNotificationBuilder {
        Notification innerNotification;

        Notification notification() {
            if (innerNotification == null)
                innerNotification = new Notification();
            return innerNotification;
        }

        public PanelNotificationBuilder and() {
            return this;
        }

        public PanelNotificationBuilder send(Long userId) {
            if (userId == null)
                return this;
            if (notification().getTitle() == null || notification().getTitle().isEmpty())
                return this;
            newNotification(userId, notification().getTitle(), notification().getBody(), notification().getLink());
            return this;
        }

        public PanelNotificationBuilder send(User user) {
            if (user != null) {
                send(user.getId());
            }
            return this;
        }

        public PanelNotificationBuilder sendOrg(Long orgId) {
            if (orgId != null) {
                List<User> users = userService.getUsersByOrganizationId(orgId);
                send(users);
            }
            return this;
        }

        public PanelNotificationBuilder send(String role) {
            Role dbRole = userService.getRoleByName(role);
            return send(dbRole);
        }

        public PanelNotificationBuilder send(Role role) {
            if (role != null) {
                List<User> users = userService.getUsersByRole(role);
                send(users);
            }
            return this;
        }

        public PanelNotificationBuilder send() {
            if (notification().getUserId() != null) {
                send(notification().getUserId());
            }
            return this;
        }

        private PanelNotificationBuilder send(List<User> users) {
            if (users != null && users.size() > 0) {
                for (User user : users) {
                    send(user);
                }
            }
            return this;
        }
    }

}