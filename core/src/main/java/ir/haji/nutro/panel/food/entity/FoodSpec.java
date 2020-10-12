package ir.haji.nutro.panel.food.entity;


import ir.haji.nutro.util.SpecificationUtil;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Created by Saeed on 3/13/17.
 */
public class FoodSpec extends SpecificationUtil implements Specification<Food> {
    String name;
    Long itemNumber;

    @Override
    public String getDefaultSort() {
        return "name,desc";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(Long itemNumber) {
        this.itemNumber = itemNumber;
    }

    @Override
    public Predicate toPredicate(Root<Food> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Predicate result = null;
        if (itemNumber != null) {
            Predicate predicate = cb.equal(root.<Long>get("itemNumber"), this.itemNumber);
            result = and(result, predicate, cb);
        }
        if (name != null && !name.isEmpty()) {
            Predicate predicate = cb.like(root.get("name"), "%" + this.name + "%");
            result = and(result, predicate, cb);
        }
        return result;
    }
}
