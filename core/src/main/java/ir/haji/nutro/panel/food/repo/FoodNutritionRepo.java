package ir.haji.nutro.panel.food.repo;

import ir.haji.nutro.panel.food.entity.FoodNutrition;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface FoodNutritionRepo extends PagingAndSortingRepository<FoodNutrition, Long>, JpaSpecificationExecutor<FoodNutrition> {
    @Transactional
    void deleteByFoodId(Long foodId);
}
