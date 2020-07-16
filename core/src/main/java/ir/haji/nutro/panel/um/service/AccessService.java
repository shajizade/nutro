package ir.haji.nutro.panel.um.service;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.um.entity.Permission;
import ir.haji.nutro.panel.um.entity.Role;
import ir.haji.nutro.panel.um.repo.PermissionRepository;
import ir.haji.nutro.panel.um.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Saeed on 3/11/17.
 */
@Service
public class AccessService {
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    List<Role> predefinedRoles;

    public Boolean authorizedRole(List<Role> userRoles, Role role) {
        for (Role userRole : userRoles) {
            if (existRoleInHierarchy(userRole, role))
                return true;
        }
        return false;
    }

    protected Boolean existRoleInHierarchy(Role userRole, Role role) {
        if (userRole.equals(role))
            return true;
        if (userRole.getParent() != null)
            return existRoleInHierarchy(userRole.getParent(), role);
        return false;

    }

    public List<Role> getPredefinedRoles() {
        return predefinedRoles;
    }

    public Page<Permission> getAllPermission(Pageable pageable) {
        return permissionRepository.findAll(pageable);
    }

    public void editCaptionOfPermission(Long permissionId, String caption) {
        Permission permission = getPermission(permissionId);
        permission.setCaption(caption);
        permissionRepository.save(permission);
    }

    public void deleteFromPermissionRole(Long permissionId, String roleId) {
        Role role = getRole(roleId);
        Permission permission = getPermission(permissionId);
        role.getPermissions().remove(permission);
        roleRepository.save(role);
    }

    public void assignPermissionToRole(String roleId, Long permissionId) {
        Role role = getRole(roleId);
        Permission permission = getPermission(permissionId);

        List<Permission> permissionsOfRole = role.getPermissions();
        boolean isExistPermission = false;
        for (Permission permissionOfRole : permissionsOfRole) {
            if (Objects.equals(permissionOfRole.getId(), permission.getId())) {
                isExistPermission = true;
                break;
            }
        }
        if (!isExistPermission) {
            role.getPermissions().add(permission);
            roleRepository.save(role);
        }
    }

    public void assignAllPermissionsToRole(String roleId) {
        Role role = getRole(roleId);
        List<Permission> allPermissions = (List<Permission>) permissionRepository.findAll();
        List<Permission> invalidPermissions = new ArrayList<>();
        for (Permission permission : allPermissions)
            if (!permission.getValid()) invalidPermissions.add(permission);

        allPermissions.removeAll(invalidPermissions);
        role.setPermissions(allPermissions);
        roleRepository.save(role);
    }

    public void editCaptionOfRole(String roleId, String caption) {
        Role role = getRole(roleId);
        role.setCaption(caption);
        roleRepository.save(role);
    }

    public Role createRole(Role role) {
        if (!role.getRole().matches("^[_A-Z]+$"))
            throw new BadRequestException("کاراکترهای مورد استفاده برای نقش مجاز نیست");
        if (role.getRole() == null)
            throw new BadRequestException("باید نقش وارد شود");
        if (role.getCaption() == null)
            throw new BadRequestException("عنوان نقش باید وارد شود");
        return roleRepository.save(role);
    }

    public List<Role> getAllRoles() {
        return (List<Role>) roleRepository.findAll();
    }

    public Role getRole(String roleId) {
        Role role = roleRepository.getRole(roleId);
        if (role == null)
            throw new BadRequestException("نقشی با شناسه" + ":[" + roleId + "]" + " یافت نشد");
        return role;
    }

    public void deleteRole(String roleId) {
        List<Role> predefinedRoles = getPredefinedRoles();
        Role role = getRole(roleId);
        if (predefinedRoles.contains(role)) {
            throw new BadRequestException("حذف نقش های از پیش تعریف شده امکان پذیر نیست");
        }
        roleRepository.delete(role);
    }

    public Permission getPermission(Long permissionId) {
        Permission permission = permissionRepository.findById(permissionId).get();
        if (permission == null)
            throw new BadRequestException("سطح دسترسی با شناسه" + ":[" + permissionId + "]" + " یافت نشد");
        return permission;
    }

}
