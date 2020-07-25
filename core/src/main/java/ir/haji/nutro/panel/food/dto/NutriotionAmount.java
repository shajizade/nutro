package ir.haji.nutro.panel.food.dto;

import ir.haji.nutro.panel.food.entity.Nutrition;
import ir.haji.nutro.util.Doubler;

/**
 * Created by jalil on 7/22/2020.
 */
public class NutriotionAmount {
    Nutrition nutrition;
    Doubler amount;

    public NutriotionAmount(Nutrition nutrition, Doubler amount) {
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
