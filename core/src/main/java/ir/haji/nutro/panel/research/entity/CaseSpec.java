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
public class CaseSpec extends SpecificationUtil implements Specification<Case> {
    String name;
    String code;
    Long researchId;

    @Override
    public String getDefaultSort() {
        return "registerDate,desc";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getResearchId() {
        return researchId;
    }

    public void setResearchId(Long researchId) {
        this.researchId = researchId;
    }

    @Override
    public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate result = null;
        if (researchId != null) {
            Predicate predicate = cb.equal(root.<Long>get("researchId"), this.researchId);
            result = and(result, predicate, cb);
        }
        if (name != null && !name.isEmpty()) {
            Predicate predicate = cb.like(root.get("name"), "%" + this.name + "%");
            result = and(result, predicate, cb);
        }
        if (code != null && !code.isEmpty()) {
            Predicate predicate = cb.like(root.get("code"), "%" + this.code + "%");
            result = and(result, predicate, cb);
        }
        return result;
    }
}
