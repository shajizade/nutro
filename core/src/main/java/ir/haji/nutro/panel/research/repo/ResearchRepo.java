package ir.haji.nutro.panel.research.repo;

import ir.haji.nutro.panel.research.entity.Research;
import ir.haji.nutro.panel.research.entity.ResearchType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Saeedon 1/14/2018.
 */
@RepositoryRestResource(exported = false)
public interface ResearchRepo extends PagingAndSortingRepository<Research, Long>, JpaSpecificationExecutor<Research> {
    @Query("select rt from ResearchType rt")
    List<ResearchType> getAllResearchTypes();

    Integer countByUserId(Long id);
}
