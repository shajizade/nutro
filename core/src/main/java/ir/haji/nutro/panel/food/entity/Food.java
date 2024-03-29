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
    private String source;
    private String category;
    private Byte[] picture;
    private String abbreviation;
    private Long itemNumber;
    private Long userId;
    private Boolean isRecipe;

    @JoinColumn(name = "foodId", referencedColumnName = "id")
    private List<UnitUsage> unitUsages;
    public Long getId() {
        return id;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Boolean getRecipe() {
        return isRecipe;
    }

    public void setRecipe(Boolean recepie) {
        isRecipe = recepie;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<UnitUsage> getUnitUsages() {
        return unitUsages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
