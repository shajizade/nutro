package ir.haji.nutro.panel.food.repo;

import ir.haji.nutro.panel.food.entity.UnitUsage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface UnitUsageRepo extends PagingAndSortingRepository<UnitUsage, Long>, JpaSpecificationExecutor<UnitUsage> {

    List<UnitUsage> findByFoodIdAndRecipeIdAndUnitId(Long foodId, Long recipeId, Long unitId);
}
