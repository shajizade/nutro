package ir.haji.nutro.panel.food.service;

import ir.haji.nutro.panel.food.entity.Food;
import ir.haji.nutro.panel.food.entity.FoodNutrition;
import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.entity.Nutrition;
import ir.haji.nutro.panel.food.repo.FoodRepo;
import ir.haji.nutro.panel.food.repo.FullFoodRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public Food getFoodById(Long id) {
        return foodRepo.findById(id).get();
    }

    public FullFood getFullFoodById(Long id) {
        return fullFoodRepo.findById(id).get();
    }

    public String calculate(List<Long> ids) {
        StringBuilder builder = new StringBuilder();
        HashMap<Nutrition, BigDecimal> nutritions = new HashMap<>();
        for (Long id : ids) {
            FullFood fullFood = getFullFoodById(id);
            builder.append(fullFood.getFood().getName()).append("\n");
            for (FoodNutrition foodNutrition : fullFood.getNutritions()) {
                if (foodNutrition.getAmount().doubleValue() > 0) {
                    builder.append(foodNutrition.getNutrition().getName()).append(":")
                            .append(foodNutrition.getAmount()).append(" ")
                            .append(foodNutrition.getNutrition().getUnit().getName())
                            .append("\t");
                    BigDecimal sumAmount = nutritions.get(foodNutrition.getNutrition());
                    sumAmount = sumAmount == null ? foodNutrition.getAmount() : (sumAmount.add(foodNutrition.getAmount()));
                    nutritions.put(foodNutrition.getNutrition(), sumAmount);
                }
            }
            builder.append("\n");
        }
        builder.append("\n\n\n");
        for (Nutrition nutrition : nutritions.keySet()) {
            builder.append(nutrition.getName()).append(":")
                    .append(nutritions.get(nutrition)).append(" ")
                    .append(nutrition.getUnit().getName())
                    .append("\n");
        }

        return builder.toString();
    }
}