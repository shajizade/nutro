package ir.haji.nutro.panel.research.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "research_type_food")
public class ResearchTypeFood implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long foodId;
    private Long researchTypeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFoodId() {
        return foodId;
    }

    public void setFoodId(Long foodId) {
        this.foodId = foodId;
    }

    public Long getResearchTypeId() {
        return researchTypeId;
    }

    public void setResearchTypeId(Long researchTypeId) {
        this.researchTypeId = researchTypeId;
    }
}
