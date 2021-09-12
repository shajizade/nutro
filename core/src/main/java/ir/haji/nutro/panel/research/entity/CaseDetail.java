package ir.haji.nutro.panel.research.entity;

import ir.haji.nutro.panel.food.entity.FullFood;
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
    public static final String MEAL_BREAKFAST = "BREAKFAST";
    public static final String MEAL_SNACK_1 = "SNACK1";
    public static final String MEAL_LUNCH = "LUNCH";
    public static final String MEAL_SNACK_2 = "SNACK2";
    public static final String MEAL_DINNER = "DINNER";
    public static final String MEAL_SNACK_3 = "SNACK3";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long caseId;
    @JoinColumn(name = "foodId")
    private FullFood food;
    private Double amount;
    @JoinColumn(name = "unitId")
    private Unit unit;
    private String meal;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(updatable = false, insertable = false, name = "unitId", referencedColumnName = "unitId"),
            @JoinColumn(updatable = false, insertable = false, name = "foodId", referencedColumnName = "foodId")
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

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Transient
    public Double getScale() {
        if (unitUsage == null || unitUsage.getScale() == null)
            return 1d;
        return unitUsage.getScale();
    }

    @Transient
    public Double getPerDayAmount() {
        if (getAmount() == null)
            return null;
        if (getDays() == null)
            return getAmount();
        return getAmount() / getDays();
    }

    @Transient
    public Double getPerYearAmount() {
        return (getPerDayAmount() != null ? getPerDayAmount() * 365 : null);
    }
}
