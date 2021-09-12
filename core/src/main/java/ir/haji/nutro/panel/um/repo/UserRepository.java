package ir.haji.nutro.panel.um.repo;


import ir.haji.nutro.panel.um.entity.Role;
import ir.haji.nutro.panel.um.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

/**
 * Created by Saeed on 2/13/17.
 */
@RestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByPhone(String username);

    User findByEmail(String username);

    @Query("select u from User u where :role member of u.roles ")
    List<User> findByRole(@Param("role") Role role);

    List<User> findByOrgId(Long organizationId);

    User findByUsername(String username);
}
