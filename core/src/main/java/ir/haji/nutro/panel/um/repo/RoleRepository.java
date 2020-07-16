package ir.haji.nutro.panel.um.repo;


import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * @auther: Saeed Hajizade
 */
@RestResource(exported = false)
public interface RoleRepository extends PagingAndSortingRepository<Role, String> {
    @Query("Select r from Role r where r.role = :role")
    public Role getRole(@Param("role") String role);

}
