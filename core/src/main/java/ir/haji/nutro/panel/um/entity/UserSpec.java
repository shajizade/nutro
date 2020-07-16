package ir.haji.nutro.panel.um.entity;


import ir.haji.nutro.util.DefaultSortByIdSpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

/**
 * Created by Saeed on 3/13/17.
 */
public class UserSpec extends DefaultSortByIdSpecificationUtil implements Specification<User> {
    String phone;
    String email;
    String name;
    Long organizationId;
    List<Role> roles;
    Boolean employee;

    public Boolean getEmployee() {
        return employee;
    }

    public void setEmployee(Boolean employee) {
        this.employee = employee;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate result = null;
        if (name != null && !name.isEmpty()) {
            Predicate p = cb.like(root.<String>get("name"), "%" + this.name + "%");
            result = and(result, p, cb);
        }

        if (phone != null && !phone.isEmpty()) {
            Predicate p = cb.like(root.<String>get("phone"), "%" + this.phone + "%");
            result = and(result, p, cb);
        }
        if (email != null && !email.isEmpty()) {
            Predicate p = cb.like(root.<String>get("email"), "%" + this.email + "%");
            result = and(result, p, cb);
        }
        if (organizationId != null) {
            Predicate p = cb.equal(root.<Organization>get("organization").get("id"), this.organizationId);
            result = and(result, p, cb);
        }

        if (roles != null && roles.size() > 0) {
            Predicate idP = cb.isMember(this.roles.get(0), root.<Collection<Role>>get("roles"));
            result = and(result, idP, cb);
        }

        return result;
    }
}
