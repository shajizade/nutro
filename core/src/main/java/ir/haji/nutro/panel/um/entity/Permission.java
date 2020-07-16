package ir.haji.nutro.panel.um.entity;

import javax.persistence.*;

/**
 * Created by Saeed on 2/13/17.
 */
@Entity
@Table(name = "permission")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String caption;
    private Boolean valid;

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof Permission)) {
            return false;
        }
        Permission permission = (Permission) o;

        return permission.id.equals(id) && permission.name.equals(name);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Permission(String name, String caption, Boolean valid) {
        this.name = name;
        this.caption = caption;
        this.valid = valid;
    }

    public Permission() {
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
