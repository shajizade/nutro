package ir.haji.nutro.panel.food.dto;

import ir.haji.nutro.panel.food.entity.Nutrition;
import ir.haji.nutro.util.Doubler;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Saeed on 7/22/2020.
 */
public class NutritionFacts implements Iterable<NutritionAmount> {
    Map<Nutrition, NutritionAmount> nutritions = new HashMap<>();

    public void add(Nutrition nutrition, Double amount) {
        add(nutrition, amount, 1.0);
    }

    public void add(Nutrition nutrition, Double amount, Double... coefficients) {
        NutritionAmount nutAmount = nutritions.get(nutrition);
        if (nutAmount == null) {
            nutAmount = new NutritionAmount(nutrition, new Doubler());
            nutritions.put(nutrition, nutAmount);
        }
        Doubler thisAmount = new Doubler(amount);
        for (Double coefficient : coefficients) {
            thisAmount.multiply(coefficient);
        }
        nutAmount.getAmount().add(thisAmount);
    }

    @Override
    public Iterator<NutritionAmount> iterator() {
        return nutritions.values().iterator();
    }

    public void add(NutritionFacts facts) {
        add(facts, 1d);
    }

    public void add(NutritionFacts recipeFacts, Double... coefficients) {
        for (NutritionAmount facts : recipeFacts) {
            add(facts.getNutrition(), facts.getAmount().toDouble(), coefficients);
        }

    }
}
