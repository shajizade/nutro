package ir.haji.nutro.panel.research.repo;

import ir.haji.nutro.panel.research.entity.SimpleCaseDetail;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface SimpleCaseDetailRepo extends PagingAndSortingRepository<SimpleCaseDetail, Long>, JpaSpecificationExecutor<SimpleCaseDetail> {
}
