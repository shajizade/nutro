package ir.haji.nutro.panel.food.service;

import ir.haji.nutro.CommonUtil;
import ir.haji.nutro.dto.DataTypeObject;
import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.food.dto.NutritionFacts;
import ir.haji.nutro.panel.food.entity.*;
import ir.haji.nutro.panel.food.repo.FoodNutritionRepo;
import ir.haji.nutro.panel.food.repo.FoodRepo;
import ir.haji.nutro.panel.food.repo.FullFoodRepo;
import ir.haji.nutro.panel.food.repo.RecipeRowRepo;
import ir.haji.nutro.util.Doubler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Saeed on 1/16/2018.
 */
@Service
public class FoodService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    FoodRepo foodRepo;
    @Autowired
    FullFoodRepo fullFoodRepo;
    @Autowired
    private RecipeRowRepo recipeRowRepo;
    @Autowired
    private FoodNutritionRepo foodNutritionRepo;
    @Autowired
    private UnitService unitService;

    public Food getFoodById(Long id) {
        return foodRepo.findById(id).get();
    }

    public FullFood getFullFoodById(Long id) {
        return fullFoodRepo.findById(id).get();
    }

    public FullFood getFullFoodByItemNumber(Long itemNumber) {
        return fullFoodRepo.findByFood_ItemNumber(itemNumber);
    }

    public List<DataTypeObject> calculate(List<DataTypeObject> foods, Double portionGrams) {
        List<DataTypeObject> result = new ArrayList<>();
        HashMap<Nutrition, Doubler> nutritions = new HashMap<>();
        Doubler grams = new Doubler();
        for (DataTypeObject food : foods) {
            FullFood fullFood = getFullFoodByItemNumber(CommonUtil.castToLong(food.get("number")));
            Double gram = CommonUtil.castToDouble(food.get("gram"));
            grams.add(gram);

            for (FoodNutrition foodNutrition : fullFood.getNutritions()) {
                if (foodNutrition.getAmount().doubleValue() > 0) {
                    Doubler sumAmount = nutritions.get(foodNutrition.getNutrition());
                    Doubler amount = new Doubler(foodNutrition.getAmount()).multiply(gram);
                    sumAmount = amount.add(sumAmount);
                    nutritions.put(foodNutrition.getNutrition(), sumAmount);
                }
            }
        }

        for (Nutrition nutrition : nutritions.keySet()) {
            Doubler value = nutritions.get(nutrition);
            value = value.divide(grams).multiply(portionGrams);
            DataTypeObject row = new DataTypeObject();
            row.set("nutrition", nutrition)
                    .set("amount", value);
            result.add(row);
        }
        return result;
    }

    public void updateRecipe(Long recipeId, List<DataTypeObject> foods) {
        Food recipe = getFoodById(recipeId);
        if (!recipe.getRecipe())
            throw new BadRequestException("این غذا امکان این عملیات را ندارد");

        recipeRowRepo.deleteByRecipeId(recipeId);
        HashMap<Nutrition, Doubler> nutritions = new HashMap<>();
        Doubler totalWeghit = new Doubler();
        List<RecipeRow> rows = new ArrayList<>();
        for (DataTypeObject food : foods) {
            FullFood fullFood = getFullFoodById(CommonUtil.castToLong(food.get("foodId")));
            Double gram = CommonUtil.castToDouble(food.get("gram"));
            RecipeRow row = new RecipeRow();
            row.setRecipeId(recipeId);
            row.setAmount(gram);
            row.setFood(fullFood);
            rows.add(row);

            totalWeghit.add(gram);

            for (FoodNutrition foodNutrition : fullFood.getNutritions()) {
                if (foodNutrition.getAmount().doubleValue() > 0) {
                    Doubler sumAmount = nutritions.get(foodNutrition.getNutrition());
                    Doubler amount = new Doubler(foodNutrition.getAmount()).multiply(gram);
                    sumAmount = amount.add(sumAmount);
                    nutritions.put(foodNutrition.getNutrition(), sumAmount);
                }
            }
        }
        recipeRowRepo.saveAll(rows);

        foodNutritionRepo.deleteByFoodId(recipeId);
        List<FoodNutrition> newFoodNutriotions = new ArrayList<>();
        for (Nutrition nutrition : nutritions.keySet()) {
            Doubler value = nutritions.get(nutrition);
            value = value.divide(totalWeghit).multiply(100);
            FoodNutrition foodNutrition = new FoodNutrition();
            foodNutrition.setFoodId(recipeId);
            foodNutrition.setAmount(value.toBigDecimal());
            foodNutrition.setNutrition(nutrition);
            newFoodNutriotions.add(foodNutrition);
        }
        foodNutritionRepo.saveAll(newFoodNutriotions);
    }

    public NutritionFacts getRecipeIngredients(Long id) {
        NutritionFacts nutritionFacts = new NutritionFacts();
        List<RecipeRow> rows = recipeRowRepo.findByRecipeId(id);
        for (RecipeRow recipeRow : rows) {
            FullFood food = recipeRow.getFood();
            for (FoodNutrition foodNutrition : food.getNutritions()) {
                nutritionFacts.add(foodNutrition.getNutrition(), foodNutrition.getAmount().doubleValue());
            }
        }
        return nutritionFacts;
    }
    public Page<Food> searchFood(FoodSpec specification) {
        return foodRepo.findAll(specification, specification.getPageable());
    }

    public void setFoodUsages(Long id, List<UnitUsage> usages) {
        for (UnitUsage usage : usages) {
            if (usage.getScale() == null || usage.getScale() <= 0)
                throw new BadRequestException("مقیاس غلط");
            if (usage.getUnitId() == null)
                throw new BadRequestException("واحد نباید خالی باشد");
            unitService.getUnitById(usage.getUnitId());
            usage.setId(null);
            usage.setFoodId(id);
        }
        unitService.save(usages);
    }
}