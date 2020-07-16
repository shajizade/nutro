package ir.haji.nutro.panel.notification.repo;

import ir.haji.nutro.panel.notification.entity.SubscriptionEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RestResource(exported = false)
public interface SubscriptionRepo extends PagingAndSortingRepository<SubscriptionEntity, Long>, JpaSpecificationExecutor<SubscriptionEntity> {
    List<SubscriptionEntity> findByEndpoint(String endpoint);

    List<SubscriptionEntity> findAll();

    void deleteByEndpoint(String endpoint);

    List<SubscriptionEntity> findByUserId(Long userId);
}
