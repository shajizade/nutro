package ir.haji.nutro.panel.food.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "food")
public class Food implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Byte[] picture;
    private String abbreviation;
    private Long itemNumber;
    @JoinColumn(name = "foodId", referencedColumnName = "id")
    private List<UnitUsage> unitUsages;
    public Long getId() {
        return id;
    }

    public List<UnitUsage> getUnitUsages() {
        return unitUsages;
    }

    public void setUnitUsages(List<UnitUsage> unitUsages) {
        this.unitUsages = unitUsages;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Byte[] getPicture() {
        return picture;
    }

    public void setPicture(Byte[] picture) {
        this.picture = picture;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public Long getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Long itemNumber) {
        this.itemNumber = itemNumber;
    }
}
