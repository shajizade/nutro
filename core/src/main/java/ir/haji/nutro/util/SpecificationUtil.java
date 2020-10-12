package ir.haji.nutro.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * Created by Saeed on 10/29/2016.
 */
public class SpecificationUtil {
    Integer page;
    Integer size;
    String sort;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Predicate and(Predicate result, Predicate newPredicate, CriteriaBuilder cb) {
        if (result == null)
            return newPredicate;
        return cb.and(result, newPredicate);
    }

    public Predicate or(Predicate result, Predicate newPredicate, CriteriaBuilder cb) {
        if (result == null)
            return newPredicate;
        return cb.or(result, newPredicate);
    }

    public Pageable getPageable() {
        Sort sort = getSortObject();
        if (page == null || page < 0)
            page = 0;
        if (size == null || size < 1)
            size = 100;
        return PageRequest.of(page, size, sort);
    }

    public String getDefaultSort() {
        return null;
    }
    private Sort getSortObject() {
        if (sort == null || sort.isEmpty() || sort.trim().equals(",")) {
            if (getDefaultSort() != null)
                sort = getDefaultSort();
            else
                return null;
        }
        String[] parts = sort.split(",");
        if (parts.length == 0)
            return null;
        Sort.Direction direction = Sort.Direction.ASC;
        if (parts.length > 1 && parts[1] != null && parts[1].trim().toLowerCase().equals("desc"))
            direction = Sort.Direction.DESC;
        return Sort.by(direction, parts[0]);
    }

}