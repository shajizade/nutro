package ir.haji.nutro.panel.um.conf;

import ir.haji.nutro.panel.um.entity.Permission;
import ir.haji.nutro.panel.um.entity.Role;
import ir.haji.nutro.panel.um.entity.User;
import ir.haji.nutro.panel.um.repo.PermissionRepository;
import ir.haji.nutro.panel.um.repo.UserRepository;
import ir.haji.nutro.panel.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Objects;


/**
 * Created by Saeed on 2/14/17.
 */
@Component
public class PermissionEvaluatorImp implements PermissionEvaluator {

    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Value("${um.publicPaths}")
    String publicPaths;

    public String[] getPublicPaths() {
        if (publicPaths == null || publicPaths.isEmpty())
            return new String[0];
        return publicPaths.split(",");
    }

    @Override
    public boolean hasPermission(org.springframework.security.core.Authentication authentication, Object o, Object permissionName) {
        if (permissionName instanceof String) {

            if (authentication != null) {
                User user = userService.getCurrentUser();
                if (user == null) return false;
                for (Role role : userService.findByUserId(user.getId()).getRoles()) {
                    for (Permission permissionMember : role.getRefinedPermissions()) {
                        if (Objects.equals(permissionMember.getName(), permissionName))
                            return true;
                    }
                }
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean hasPermission(org.springframework.security.core.Authentication authentication, Serializable serializable, String s, Object o) {
        return false;
    }
}
