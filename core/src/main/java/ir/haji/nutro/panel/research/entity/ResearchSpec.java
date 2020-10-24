package ir.haji.nutro.panel.research.entity;


import ir.haji.nutro.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Saeed on 3/13/17.
 */
public class ResearchSpec extends SpecificationUtil implements Specification<Research> {
    String name;
    Long userId;

    @Override
    public String getDefaultSort() {
        return "createDate,desc";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public Predicate toPredicate(Root<Research> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate result = null;
        if (userId != null) {
            Predicate predicate = cb.equal(root.<Long>get("researchId"), this.userId);
            result = and(result, predicate, cb);
        }
        if (name != null && !name.isEmpty()) {
            Predicate predicate = cb.like(root.get("name"), "%" + this.name + "%");
            result = and(result, predicate, cb);
        }
        return result;
    }
}
