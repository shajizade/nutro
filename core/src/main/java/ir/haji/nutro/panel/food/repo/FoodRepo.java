package ir.haji.nutro.panel.food.repo;

import ir.haji.nutro.panel.food.entity.Food;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface FoodRepo extends PagingAndSortingRepository<Food, Long>, JpaSpecificationExecutor<Food> {
    @Query(nativeQuery = true,
            value = "select food.id,food.name " +
                    "from research_type_food , food  where research_type_food.foodId=food.id and research_type_food.researchTypeId= ?1 ")
    List<Object[]> getFoodsArray(Long researchTypeId);

    @Query("select f " +
            "from Food f ,ResearchTypeFood rtf " +
            "where rtf.foodId=f.id and rtf.researchTypeId= :researchTypeId ")
    List<Food> getFoodsOfResearch(@Param("researchTypeId") Long researchTypeId);
}
