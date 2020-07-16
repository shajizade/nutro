package ir.haji.nutro.panel.um.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saeed on 2/13/17.
 */

@Entity
@Table(name = "role")
public class Role implements GrantedAuthority {
    @Id
    private String role;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "role_permission", joinColumns = @JoinColumn(name = "role", referencedColumnName = "role"), inverseJoinColumns = @JoinColumn(name = "permissionId", referencedColumnName = "id"))
    private List<Permission> permissions;
    private String caption;
    @JoinColumn(name = "parent")
    private Role parent;

    @JsonIgnore
    @Transient
    List<Permission> refinedPermissions = new ArrayList<Permission>();

    @Transient
    Boolean insertedRefinedPermissions = false;


    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    @Transient
    public boolean hasParent(Role role) {
        if (parent == null)
            return false;
        if (parent.equals(role))
            return true;
        return parent.hasParent(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role1 = (Role) o;

        if (role != null ? !role.equals(role1.role) : role1.role != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return role != null ? role.hashCode() : 0;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    @JsonIgnore
    public String getAuthority() {
        return role;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public List<Permission> getRefinedPermissions() {
        if (!insertedRefinedPermissions) {
            refinedPermissions.addAll(permissions);
            Role parentRoleTemp = parent;
            while (parentRoleTemp != null) {
                refinedPermissions.addAll(parentRoleTemp.getPermissions());
                parentRoleTemp = parentRoleTemp.getParent();
            }
            insertedRefinedPermissions = true;
        }
        return refinedPermissions;
    }

    public String getCaption() {
        return caption;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Role getParent() {
        return parent;
    }

    public void setParent(Role parent) {
        this.parent = parent;
    }

    public Role(String role, String caption, Role parent) {
        this.role = role;
        this.caption = caption;
        this.parent = parent;
    }

}
