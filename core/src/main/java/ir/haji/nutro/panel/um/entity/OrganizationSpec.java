package ir.haji.nutro.panel.um.entity;


import ir.haji.nutro.util.DefaultSortByIdSpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


public class OrganizationSpec extends DefaultSortByIdSpecificationUtil implements Specification<Organization> {
    String name;
    String officialName;
    Long id;

    public String getOfficialName() {
        return officialName;
    }

    public void setOfficialName(String officialName) {
        this.officialName = officialName;
    }

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

    @Override
    public Predicate toPredicate(Root<Organization> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate result = null;
        if (name != null && !name.isEmpty()) {
            Predicate p = cb.like(root.<String>get("name"), "%" + this.name + "%");
            p = cb.or(p, cb.like(root.<String>get("officialName"), "%" + this.name + "%"));
            p = cb.or(p, cb.like(root.<String>get("description"), "%" + this.name + "%"));
            result = and(result, p, cb);
        }
        if (officialName != null && !officialName.isEmpty()) {
            Predicate p = cb.like(root.<String>get("officialName"), "%" + this.officialName + "%");
            result = and(result, p, cb);
        }
        if (id != null) {
            Predicate p = cb.equal(root.<Long>get("id"), id);
            result = and(result, p, cb);
        }
        return result;
    }
}
