package ir.haji.nutro;

import ir.haji.nutro.panel.notification.service.NotificationService;
import ir.haji.nutro.panel.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by User on 8/14/2018.
 */
@Service
public class ScheduledRoutine {
    @Autowired
    private UserService userService;
    @Autowired
    private NotificationService notificationService;

}
