package ir.haji.nutro.panel.um.repo;


import ir.haji.nutro.panel.um.entity.Organization;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Created by Saeed on 2/13/17.
 */
@RestResource(exported = false)
public interface OrganizationRepository extends PagingAndSortingRepository<Organization, Long>, JpaSpecificationExecutor<Organization> {
}
