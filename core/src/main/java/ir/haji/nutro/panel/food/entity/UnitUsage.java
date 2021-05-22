package ir.haji.nutro.panel.food.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saeed on 1/13/2018.
 */
@Entity
@Table(name = "unit_usage")
public class UnitUsage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long unitId;
    private Long foodId;
    private Double scale;
    @JoinColumn(name = "unitId", insertable = false, updatable = false)
    private Unit unit;
    public UnitUsage() {
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public UnitUsage(Long foodId, Long unitId, Double scale) {
        this.unitId = unitId;
        this.foodId = foodId;
        this.scale = scale;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Double getScale() {
        return scale;
    }

    public void setScale(Double scale) {
        this.scale = scale;
    }

}
