package ir.haji.nutro.panel.research.entity;

import ir.haji.nutro.panel.food.entity.FullFood;
import ir.haji.nutro.panel.food.entity.Recipe;
import ir.haji.nutro.panel.food.entity.Unit;
import ir.haji.nutro.panel.food.entity.UnitUsage;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "case_detail")
public class CaseDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long caseId;
    @JoinColumn(name = "foodId")
    private FullFood food;
    @JoinColumn(name = "recipeId")
    private Recipe recipe;
    private Double amount;
    @JoinColumn(name = "unitId")
    private Unit unit;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(updatable = false, insertable = false, name = "unitId", referencedColumnName = "unitId"),
            @JoinColumn(updatable = false, insertable = false, name = "foodId", referencedColumnName = "foodId"),
            @JoinColumn(updatable = false, insertable = false, name = "recipeId", referencedColumnName = "recipeId")
    })
    private UnitUsage unitUsage;
    private Integer days;

    public UnitUsage getUnitUsage() {
        return unitUsage;
    }

    public void setUnitUsage(UnitUsage unitUsage) {
        this.unitUsage = unitUsage;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCaseId() {
        return caseId;
    }

    public void setCaseId(Long caseId) {
        this.caseId = caseId;
    }

    public FullFood getFood() {
        return food;
    }

    public void setFood(FullFood food) {
        this.food = food;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

}
