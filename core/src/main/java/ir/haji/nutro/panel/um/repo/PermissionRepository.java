package ir.haji.nutro.panel.um.repo;


import ir.haji.nutro.panel.um.entity.Permission;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Saeed on 2/13/17.
 */
@RestResource(exported = false)
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long> {
    List<Permission> findByName(String name);

    @Modifying
    @Transactional
    @Query("update Permission p set p.valid = :valid where p.name = :name")
    void setValid(@Param("name") String name, @Param("valid") Boolean valid);
}
