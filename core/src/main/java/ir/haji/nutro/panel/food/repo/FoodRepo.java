package ir.haji.nutro.panel.food.repo;

import ir.haji.nutro.panel.food.entity.Food;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface FoodRepo extends PagingAndSortingRepository<Food, Long>, JpaSpecificationExecutor<Food> {
}
