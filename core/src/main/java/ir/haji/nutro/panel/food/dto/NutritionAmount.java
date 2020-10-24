package ir.haji.nutro.panel.food.dto;

import ir.haji.nutro.panel.food.entity.Nutrition;
import ir.haji.nutro.util.Doubler;

/**
 * Created by Saeed on 7/22/2020.
 */
public class NutritionAmount {
    Nutrition nutrition;
    Doubler amount;

    public NutritionAmount(Nutrition nutrition, Doubler amount) {
        this.nutrition = nutrition;
        this.amount = amount;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public Doubler getAmount() {
        return amount;
    }

    public void setAmount(Doubler amount) {
        this.amount = amount;
    }
}
