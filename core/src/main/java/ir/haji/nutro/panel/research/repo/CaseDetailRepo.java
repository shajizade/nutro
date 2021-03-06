package ir.haji.nutro.panel.research.repo;

import ir.haji.nutro.panel.research.entity.CaseDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface CaseDetailRepo extends PagingAndSortingRepository<CaseDetail, Long>, JpaSpecificationExecutor<CaseDetail> {
    List<CaseDetail> findByCaseId(Long id);

    @Transactional
    void deleteByCaseIdAndFood_Id(Long caseId, Long foodId);

}
