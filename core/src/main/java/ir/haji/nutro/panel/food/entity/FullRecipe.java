package ir.haji.nutro.panel.food.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "recipe")
public class FullRecipe implements Serializable {
    @Id
    private Long id;

    @JoinColumn(name = "id", insertable = false, updatable = false)
    private Recipe recipe;

    @OneToMany
    @JoinColumn(name = "recipeId")
    List<RecipeRow> foods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public List<RecipeRow> getFoods() {
        return foods;
    }

    public void setFoods(List<RecipeRow> foods) {
        this.foods = foods;
    }
}
