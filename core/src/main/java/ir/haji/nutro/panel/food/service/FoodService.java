package ir.haji.nutro.panel.food.service;

import ir.haji.nutro.CommonUtil;
import ir.haji.nutro.dto.DataTypeObject;
import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.food.dto.NutritionFacts;
import ir.haji.nutro.panel.food.entity.*;
import ir.haji.nutro.panel.food.repo.FoodRepo;
import ir.haji.nutro.panel.food.repo.FullFoodRepo;
import ir.haji.nutro.panel.food.repo.FullRecipeRepo;
import ir.haji.nutro.panel.food.repo.RecipeRepo;
import ir.haji.nutro.util.Doubler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

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
    private RecipeRepo recipeRepo;
    @Autowired
    private FullRecipeRepo fullRecipeRepo;

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

    public FullRecipe createRecipe(FullRecipe fullRecipe) {
        fullRecipe.getRecipe().setId(null);
        recipeRepo.save(fullRecipe.getRecipe());
        fullRecipe.setId(fullRecipe.getRecipe().getId());

        Doubler weight = new Doubler();
        for (RecipeRow recipeRow : fullRecipe.getFoods()) {
            weight.add(recipeRow.getAmount());
        }
        for (RecipeRow recipeRow : fullRecipe.getFoods()) {
            recipeRow.setId(null);
            recipeRow.setRecipeId(fullRecipe.getRecipe().getId());
            recipeRow.setAmount(new Doubler(recipeRow.getAmount()).divide(weight).toDouble());
        }
        return fullRecipeRepo.save(fullRecipe);
    }

    public NutritionFacts getRecipe(Long id) {
        NutritionFacts nutritionFacts = new NutritionFacts();
        FullRecipe fullRecipe = getFullRecipe(id);
        for (RecipeRow recipeRow : fullRecipe.getFoods()) {
            FullFood food = recipeRow.getFood();
            for (FoodNutrition foodNutrition : food.getNutritions()) {
                nutritionFacts.add(foodNutrition.getNutrition(), foodNutrition.getAmount().doubleValue());
            }
        }
        return nutritionFacts;
    }

    private FullRecipe getFullRecipe(Long id) {
        Optional<FullRecipe> recipe = fullRecipeRepo.findById(id);
        if (!recipe.isPresent())
            throw new BadRequestException("این رسپی وجود ندارد");
        return recipe.get();
    }

    public Page<Food> searchFood(FoodSpec specification) {
        return foodRepo.findAll(specification, specification.getPageable());
    }
}