package ir.haji.nutro.panel.food.controller;

import ir.haji.nutro.dto.DataTypeObject;
import ir.haji.nutro.panel.food.dto.NutritionFacts;
import ir.haji.nutro.panel.food.entity.Food;
import ir.haji.nutro.panel.food.entity.FoodSpec;
import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.entity.UnitUsage;
import ir.haji.nutro.panel.food.service.FoodService;
import ir.haji.nutro.panel.um.predefined.AdminRole;
import ir.haji.nutro.panel.um.predefined.BasicRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;


/**
 * Created by Saeedon 1/15/2018.
 */
@RestController
@RequestMapping("food")
public class FoodController {
    @Autowired
    FoodService foodService;

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    private Page<Food> search(FoodSpec specification) {
        return foodService.searchFood(specification);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    private Food getById(@PathVariable Long id) {
        return foodService.getFoodById(id);
    }

    @RolesAllowed({AdminRole.NAME})
    @RequestMapping(value = "/{id}/unitUsage", method = RequestMethod.PUT)
    private void updateUnitUsages(@PathVariable Long id, @RequestBody List<UnitUsage> usages) {
        foodService.setFoodUsages(id, usages);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/full", method = RequestMethod.GET)
    private FullFood getFullById(@PathVariable Long id) {
        return foodService.getFullFoodById(id);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/calculate", method = RequestMethod.PUT)
    private List<DataTypeObject> calculate(@RequestBody List<DataTypeObject> foods, @RequestParam Double portionGrams) {
        return foodService.calculate(foods, portionGrams);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/ingredients", method = RequestMethod.POST)
    private void updateRecipe(@PathVariable Long id, @RequestBody List<DataTypeObject> foods) {
        foodService.updateRecipe(id, foods);
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/{id}/ingredients", method = RequestMethod.GET)
    private NutritionFacts getRecipe(@PathVariable Long id) {
        return foodService.getRecipeIngredients(id);
    }

}
