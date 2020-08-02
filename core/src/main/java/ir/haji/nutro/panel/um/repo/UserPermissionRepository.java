package ir.haji.nutro.panel.um.repo;

import ir.haji.nutro.panel.um.dto.UserPermissonDtoSerializable;
import ir.haji.nutro.panel.um.entity.Permission;
import ir.haji.nutro.panel.um.entity.UserPermission;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 * Created by Saeed on 2/13/17.
 */
@RepositoryRestResource(exported = false)
public interface UserPermissionRepository extends PagingAndSortingRepository<UserPermission, UserPermissonDtoSerializable>, JpaSpecificationExecutor<UserPermission> {
    List<UserPermission> findByUserId(Long userId);
    UserPermission findByUserIdAndPermission(Long userId, Permission permission);
}
