package ir.haji.nutro.panel.research.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Saeedon 1/13/2018.
 */
@Entity
@Table(name = "research_type")
public class ResearchType implements Serializable {
    public static final long FREQUENCY_24_ID = 2l;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Transient
    public Boolean getFoodFree() {
        return id.equals(FREQUENCY_24_ID);
    }
}
