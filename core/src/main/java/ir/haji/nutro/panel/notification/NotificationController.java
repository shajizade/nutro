package ir.haji.nutro.panel.notification;

import ir.haji.nutro.panel.notification.dto.Subscription;
import ir.haji.nutro.panel.notification.entity.Notification;
import ir.haji.nutro.panel.notification.service.NotificationService;
import ir.haji.nutro.panel.notification.service.SubscriptionService;
import ir.haji.nutro.panel.um.predefined.BasicRole;
import ir.haji.nutro.panel.um.predefined.SystemRole;
import ir.haji.nutro.panel.um.predefined.UnauthorizedRole;
import ir.haji.nutro.panel.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


/**
 * Created by Saeedon 1/15/2018.
 */
@RestController
@RequestMapping("notif")
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    SubscriptionService subService;
    @Autowired
    private UserService userService;

    @RolesAllowed({UnauthorizedRole.NAME})
    @GetMapping(path = "/publicSigningKey", produces = "application/octet-stream")
    public String publicSigningKey() {
        return subService.getPublicKeyBase64();
    }

    @RolesAllowed({BasicRole.NAME})
    @PostMapping("/subscribe")
    public void subscribe(@RequestBody Subscription subscription) {
        subService.subscribe(subscription);
    }

    @RolesAllowed({SystemRole.NAME})
    @RequestMapping(method = RequestMethod.POST, value = "/new")
    public void createNotification(@RequestBody(required = false) Notification notification
            , @RequestParam(required = false) String title
            , @RequestParam(required = false) String body
            , @RequestParam(required = false) String link
            , @RequestParam(required = false) Long organizationId
            , @RequestParam(required = false) String roleName) {
    }

}
