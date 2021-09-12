package ir.haji.nutro.panel.um.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Saeed on 2/13/17.
 */

@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String phone;
    private String email;
    private String name;
    @JoinColumn(name = "orgId")
    private Organization organization;
    @JsonIgnore
    @Column(insertable = false, updatable = false)
    private Long orgId;
    private String password;
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordExpired;
    private Boolean isEnable;
    private String description;
    private String username;
    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "userId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role", referencedColumnName = "role"))
    private List<Role> roles;

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @JsonIgnore
    public Date getPasswordExpired() {
        return passwordExpired;
    }

    public void setPasswordExpired(Date passwordExpired) {
        this.passwordExpired = passwordExpired;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return phone.equals(user.phone);
    }

    @Override
    public int hashCode() {
        return getUsername().hashCode();
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public Boolean hasAuthority(String roleName) {
        for (Role role : roles) {
            if (role.getRole().equals(roleName))
                return true;
        }
        return false;
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        if (isEnable == null)
            return true;
        return isEnable;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        if (passwordExpired == null)
            return true;
        Date current = new Date();
        if (current.after(passwordExpired))
            return false;
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean isEnable() {
        return isEnable;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    @Override
    public String toString() {
        return "phone: " + phone + '\n' +
                "email: " + email + '\n' +
                "name:  " + name + '\n';
    }

    public String getRolesAsString() {
        StringBuilder builder = new StringBuilder();
        for (Role role : roles) {
            builder.append(role.getRole()).append(",");
        }
        return builder.toString();
    }
}
