package ir.haji.nutro.panel.food.repo;

import ir.haji.nutro.panel.food.entity.RecipeRow;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RestResource(exported = false)
public interface RecipeRowRepo extends PagingAndSortingRepository<RecipeRow, Long>, JpaSpecificationExecutor<RecipeRow> {
    List<RecipeRow> findByRecipeId(Long id);

    @Transactional
    void deleteByRecipeId(Long recipeId);
}
