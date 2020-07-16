package ir.haji.nutro.panel.um;

import ir.haji.nutro.panel.um.entity.Permission;
import ir.haji.nutro.panel.um.entity.Role;
import ir.haji.nutro.panel.um.entity.User;
import ir.haji.nutro.panel.um.predefined.AdminRole;
import ir.haji.nutro.panel.um.predefined.UnauthorizedRole;
import ir.haji.nutro.panel.um.repo.PermissionRepository;
import ir.haji.nutro.panel.um.repo.RoleRepository;
import ir.haji.nutro.panel.um.service.AccessService;
import ir.haji.nutro.panel.um.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Saeed on 2/15/17.
 */
@Component
public class UserManagementDatabaseUpdater {
    @Autowired
    RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    UserService userService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    AccessService accessService;
    Logger logger = LoggerFactory.getLogger(UserManagementDatabaseUpdater.class);


    public void update() {
        logger.info("update user management started");
        List<Role> seenRoles = new ArrayList<>();
        for (Role role : accessService.getPredefinedRoles()) {
            if (!seenRoles.contains(role) && !role.getRole().equals(UnauthorizedRole.NAME)) {
                createOrUpdateRoleRecursive(role, seenRoles);
            }
        }
        logger.info("roles updated");
        List<Permission> allPermissions = (List<Permission>) permissionRepository.findAll();
        List<Permission> permissionPreviousValid = new ArrayList<>();

        Map<RequestMappingInfo, HandlerMethod> handlerMethods =
                this.requestMappingHandlerMapping.getHandlerMethods();
        logger.info("start processing " + handlerMethods.entrySet().size() + " permissions");
        for (Map.Entry<RequestMappingInfo, HandlerMethod> item : handlerMethods.entrySet()) {
            RequestMappingInfo mapping = item.getKey();
            HandlerMethod method = item.getValue();
            for (String urlPattern : mapping.getPatternsCondition().getPatterns()) {
                String urlComplete = mapping.getMethodsCondition() + "::" + urlPattern;
                if (hasUnauthorizedRole(method))
                    urlComplete = urlComplete + "::" + UnauthorizedRole.NAME;
                Permission dbPermission = getPermissionByName(urlComplete);
                if (dbPermission == null) {
                    try {
                        dbPermission = savePermission(mapping.getName(), urlComplete);
                    } catch (Exception exception) {
                        exception.printStackTrace();
                        throw new DataIntegrityViolationException("database Exception");
                    }
                } else {
                    permissionPreviousValid.add(dbPermission);
                }
                assignPermissionsToAllowedRoles(method, dbPermission);
            }
        }
        logger.info("Saving role permissions");
        saveRoles();
        allPermissions.removeAll(permissionPreviousValid);
        logger.info("removing " + allPermissions.size() + " permissions");
        for (Permission permission : allPermissions) {
            permissionRepository.delete(permission);
        }
        logger.info("update user management finished");
    }

    private void saveRoles() {
        roleRepository.saveAll(roleMap.values());
    }

    private boolean hasUnauthorizedRole(HandlerMethod method) {
        RolesAllowed rolesAllowedAnnotation = method.getMethodAnnotation(RolesAllowed.class);
        if (rolesAllowedAnnotation != null) {
            for (String roleAllowed : rolesAllowedAnnotation.value()) {
                if (roleAllowed.equals(UnauthorizedRole.NAME))
                    return true;
            }
        }
        return false;
    }

    HashMap<String, Permission> permissionMap = null;
    HashMap<String, Role> roleMap = null;

    private Role getRoleByName(String name) {
        if (roleMap == null) {
            roleMap = new HashMap<>();
            Iterable<Role> all = roleRepository.findAll();
            for (Role role : all) {
                role.setPermissions(new ArrayList<Permission>());
                roleMap.put(role.getRole(), role);
            }
        }
        return roleMap.get(name);

    }

    private Permission getPermissionByName(String url) {
        if (permissionMap == null) {
            permissionMap = new HashMap<>();
            Iterable<Permission> permissions = permissionRepository.findAll();
            for (Permission permission : permissions) {
                permissionMap.put(permission.getName(), permission);
            }
        }
        return permissionMap.get(url);
    }


    private void createOrUpdateRoleRecursive(Role role, List<Role> seenRoles) {
        if (role.getParent() != null && !seenRoles.contains(role.getParent()))
            createOrUpdateRoleRecursive(role.getParent(), seenRoles);
        Role parentRole = null;
        for (Role iterRole : seenRoles) {
            if (iterRole.equals(role.getParent()))
                parentRole = iterRole;
        }
        role = accessService.createRole(new Role(role.getRole(), role.getCaption(), parentRole));
        seenRoles.add(role);
    }

    void assignPermissionsToAllowedRoles(HandlerMethod method, Permission permission) {
        assignPermissionToRole(permission, AdminRole.NAME);
        RolesAllowed rolesAllowedAnnotation = method.getMethodAnnotation(RolesAllowed.class);
        if (rolesAllowedAnnotation != null) {
            for (String roleAllowed : rolesAllowedAnnotation.value()) {
                if (!roleAllowed.equals(UnauthorizedRole.NAME))
                    assignPermissionToRole(permission, roleAllowed);
            }

        }
    }

    private void assignPermissionToRole(Permission permission, String roleName) {
        Role role = getRoleByName(roleName);
        if (role != null)
            role.getPermissions().add(permission);
    }

    Permission savePermission(String methodCaption, String urlComplete) {
        Permission permission = new Permission(urlComplete, methodCaption, true);
        return permissionRepository.save(permission);
    }

    public void createAdminIfNotExists() {
        User user = userService.findByUsername("admin");
        if (user != null)
            return;
        user = new User();
        user.setPhone("admin");
        user.setName("ادمین");
        user = userService.createUser(user, "salam");
        userService.assignRoleToUser(AdminRole.NAME, user);
    }
}
