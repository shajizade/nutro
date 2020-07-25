package ir.haji.nutro.panel.food.service;

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
import org.springframework.stereotype.Service;

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

    public String calculate(List<DataTypeObject> foods, Double portionGrams) {
        StringBuilder builder = new StringBuilder();
        HashMap<Nutrition, Doubler> nutritions = new HashMap<>();
        Doubler grams = new Doubler();
        for (DataTypeObject food : foods) {
            FullFood fullFood = getFullFoodByItemNumber((Long) food.get("number"));
            Double gram = ((Double) food.get("gram"));
            grams.add(gram);
            builder.append(fullFood.getFood().getName() + " | " + fullFood.getFood().getId()).append("\n");

            builder.append("{" +
                    "\"amount\":\"" + gram + "\"," +
                    "\"food\":{\"id\":\"" + fullFood.getFood().getId() + "\"}" +
                    "}");

            for (FoodNutrition foodNutrition : fullFood.getNutritions()) {
                if (foodNutrition.getAmount().doubleValue() > 0) {
                    builder.append("[").append(foodNutrition.getNutrition().getName()).append(":")
                            .append(foodNutrition.getAmount()).append(" ")
                            .append(foodNutrition.getNutrition().getUnit().getName())
                            .append("]\t");
                    Doubler sumAmount = nutritions.get(foodNutrition.getNutrition());
                    Doubler amount = new Doubler(foodNutrition.getAmount()).multiply(gram);
                    sumAmount = amount.add(sumAmount);
                    nutritions.put(foodNutrition.getNutrition(), sumAmount);
                }
            }
            builder.append("\n");
        }
        builder.append("\n\n\n");
        for (Nutrition nutrition : nutritions.keySet()) {
            builder.append(nutrition.getName()).append(":")
                    .append(nutritions.get(nutrition).divide(grams).multiply(portionGrams).toString()).append(" ")
                    .append(nutrition.getUnit().getName())
                    .append("\n");
        }
        builder.append("وزن:").append(grams.toString()).append("\n");
        builder.append("وزن:").append(grams.divide(grams).multiply(portionGrams).toString());
        return builder.toString();
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
}