package ir.haji.nutro.panel.food.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "food")
public class FullFood implements Serializable {
    @Id
    private Long id;

    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Food food;

    @OneToMany
    @JoinColumn(name = "foodId")
    List<FoodNutrition> nutritions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public List<FoodNutrition> getNutritions() {
        return nutritions;
    }

    public void setNutritions(List<FoodNutrition> nutritions) {
        this.nutritions = nutritions;
    }
}
