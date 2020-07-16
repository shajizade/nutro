package ir.haji.nutro.panel.food.repo;

import ir.haji.nutro.panel.food.entity.FullFood;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by Saeedon 1/14/2018.
 */
@RestResource(exported = false)
public interface FullFoodRepo extends PagingAndSortingRepository<FullFood, Long>, JpaSpecificationExecutor<FullFood> {
}
