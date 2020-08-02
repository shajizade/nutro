package ir.haji.nutro.panel.research.repo;

import ir.haji.nutro.panel.research.entity.Research;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface ResearchRepo extends PagingAndSortingRepository<Research, Long>, JpaSpecificationExecutor<Research> {
}
