package ir.haji.nutro.panel.research.repo;

import ir.haji.nutro.panel.research.entity.Case;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface CaseRepo extends PagingAndSortingRepository<Case, Long>, JpaSpecificationExecutor<Case> {
    List<Case> findByResearchId(Long id);

    List<Case> findAllByResearchIdAndStatus(Long id, String statusAccepted);
}
