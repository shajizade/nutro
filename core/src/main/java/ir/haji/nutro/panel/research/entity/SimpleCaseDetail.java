package ir.haji.nutro.panel.research.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "case_detail")
public class SimpleCaseDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long caseId;
    private Long foodId;
    private Long recipeId;
    private Long unitId;
    private Double amount;
    private Integer days;

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

    public Long getFoodId() {
        if (foodId == null)
            foodId = 0l;
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId == null ? 0 : foodId;
    }

    public Long getRecipeId() {
        if (recipeId == null)
            recipeId = 0l;
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId == null ? 0 : recipeId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
