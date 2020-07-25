package ir.haji.nutro.panel.food.dto;

import ir.haji.nutro.panel.food.entity.Nutrition;
import ir.haji.nutro.util.Doubler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saeed on 7/22/2020.
 */
public class NutritionFacts implements Iterable<NutriotionAmount> {
    Map<Nutrition, NutriotionAmount> nutritions = new HashMap<>();

    public void add(Nutrition nutrition, Double amount) {
        add(nutrition, amount, 1.0);
    }

    public void add(Nutrition nutrition, Double amount, Double coefficient) {
        NutriotionAmount nutAmount = nutritions.get(nutrition);
        if (nutAmount == null) {
            nutAmount = new NutriotionAmount(nutrition, new Doubler());
            nutritions.put(nutrition, nutAmount);
        }
        nutAmount.getAmount().add(new Doubler(amount).multiply(coefficient));
    }

    @Override
    public Iterator<NutriotionAmount> iterator() {
        return nutritions.values().iterator();
    }
}
