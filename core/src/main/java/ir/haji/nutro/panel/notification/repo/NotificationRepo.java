package ir.haji.nutro.panel.notification.repo;

import ir.haji.nutro.panel.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RestResource(exported = false)
public interface NotificationRepo extends PagingAndSortingRepository<Notification, Long>, JpaSpecificationExecutor<Notification> {
    List<Notification> findBySeen(Boolean seen);
}
