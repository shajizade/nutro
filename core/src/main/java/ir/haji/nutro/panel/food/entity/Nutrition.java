package ir.haji.nutro.panel.food.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "nutrition")
public class Nutrition implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @JoinColumn(name = "unitId")
    private Unit unit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
