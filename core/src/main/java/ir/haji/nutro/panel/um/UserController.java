package ir.haji.nutro.panel.um;

import ir.haji.nutro.panel.um.dto.UserAroundDto;
import ir.haji.nutro.panel.um.entity.Permission;
import ir.haji.nutro.panel.um.entity.Role;
import ir.haji.nutro.panel.um.entity.User;
import ir.haji.nutro.panel.um.entity.UserSpec;
import ir.haji.nutro.panel.um.predefined.*;
import ir.haji.nutro.panel.um.repo.PermissionRepository;
import ir.haji.nutro.panel.um.repo.UserPermissionRepository;
import ir.haji.nutro.panel.um.service.AccessService;
import ir.haji.nutro.panel.um.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Saeed on 2/13/17.
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    AccessService accessService;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;


    /*** Register */

    @RolesAllowed({UnauthorizedRole.NAME})
    @RequestMapping(value = "getCode", method = RequestMethod.GET)
    public void getCode(@RequestParam(required = false) String username, HttpSession session) {
        userService.sendCode(username, session);
    }

    @RolesAllowed({UnauthorizedRole.NAME})
    @RequestMapping(value = "validate", method = RequestMethod.PUT)
    public void validate(@RequestParam String code, HttpSession session) {
        userService.validate(code, session);
    }

    @RolesAllowed({UnauthorizedRole.NAME})
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public void register(@RequestBody UserAroundDto user, HttpSession session) {
        userService.register(user, session);
    }

    @RolesAllowed({UnauthorizedRole.NAME})
    @RequestMapping(value = "simpleRegister", method = RequestMethod.POST)
    public void simpleRegister(@RequestBody UserAroundDto user) {
        userService.register(user);
    }

    @RolesAllowed({UnauthorizedRole.NAME})
    @RequestMapping(value = "/me", method = RequestMethod.GET)
    public User getCurrentUser(HttpServletResponse response) {
        return userService.getMe();
    }

    @RolesAllowed({BasicRole.NAME})
    @RequestMapping(value = "/me/changePassword", method = RequestMethod.PUT)
    public void changePassword(@RequestParam String lastPassword, @RequestParam String password) {
        userService.changePassword(lastPassword, password);
    }

    /*****************************************************************************

     User

     *****************************************************************************/
    @RolesAllowed(AdministrationRole.NAME)
    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET, name = "{username} دریافت اطلاعات کاربر")
    public User getUser(@PathVariable Long id) {
        return userService.findByUserId(id);
    }

    @RolesAllowed(AdministrationRole.NAME)
    @RequestMapping(value = "/user/getByRole/{roleId}", method = RequestMethod.GET, name = "{roleId} دریافت لیست کاربرانی با نقش")
    public List<User> getByRole(@PathVariable String roleId) {
        Role role = accessService.getRole(roleId);
        return userService.findByRole(role);
    }

    @RolesAllowed(AdministrationRole.NAME)
    @RequestMapping(value = "/user", method = RequestMethod.GET, name = "جستجو کاربر")
    public Page<User> getUsersList(UserSpec spec) {
        return userService.getUsersList(spec);
    }

    @RolesAllowed({AdminRole.NAME, OperationRole.NAME, SaleRole.NAME})
    @RequestMapping(value = "/user/", method = RequestMethod.POST, name = "افزودن کاربر")
    public User createUser(@RequestBody UserAroundDto user) {
        return userService.createCustomer(user.getUser(), user.getPassword());
    }

    @RolesAllowed({AdminRole.NAME, OperationRole.NAME})
    @RequestMapping(value = "/user/{username:.+}", method = RequestMethod.DELETE, name = "{username} حذف کاربر")
    public void deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
    }


    @RolesAllowed({AdminRole.NAME, OperationRole.NAME})
    @RequestMapping(value = "/user/{id}/changeEnabled", method = RequestMethod.PUT, name = "تغییر وضعیت کاربر")
    public void changeEnabledUser(@PathVariable Long id, @RequestParam Boolean enabled) {
        userService.changeEnabledUser(id, enabled);
    }

    @RolesAllowed({OperationRole.NAME})
    @RequestMapping(value = "/user/{id}/changePassword", method = RequestMethod.PUT)
    public void resetPassword(@PathVariable Long id, @RequestBody String password) {
        userService.resetPassword(id, password);
    }


    @RolesAllowed({AdminRole.NAME, OperationRole.NAME, SaleRole.NAME})
    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT, name = "{username} به روز رسانی مشخصات کاربر")
    public void updateUser(@PathVariable Long id, @RequestBody UserAroundDto user) {
        userService.updateUser(id, user.getUser());
    }

    @RolesAllowed(OperationRole.NAME)
    @RequestMapping(value = "/user/{username}/assignPermission", method = RequestMethod.POST, name = "{username} دادن مجوز به کاربر")
    public void assignPermissionToUser(@RequestParam Long permissionId, @PathVariable String username, @RequestParam Boolean denied) {
        userService.assignPermissionToUser(permissionId, username, denied);
    }

    @RolesAllowed(OperationRole.NAME)
    @RequestMapping(value = "/user/{username}/deletePermission", method = RequestMethod.DELETE, name = "{username} حذف مجوز از کاربر")
    public void deletePermissionFromUser(@RequestParam Long permissionId, @PathVariable String username) {
        userService.deleteFromPermissionUser(permissionId, username);

    }

    @RolesAllowed(OperationRole.NAME)
    @RequestMapping(value = "/user/{id}/assignRole", method = RequestMethod.POST, name = "{username} دادن نقش به کاربر")
    public void assignRoleToUser(@RequestParam String role, @PathVariable Long id) {
        userService.assignRoleToUser(role, id);
    }

    @RolesAllowed(OperationRole.NAME)
    @RequestMapping(value = "/user/{id}/updateRoles", method = RequestMethod.POST, name = "{username} دادن نقش به کاربر")
    public void assignRoleToUser(@RequestBody List<String> roles, @PathVariable Long id) {
        userService.updateUserRoles(roles, id);
    }

    @RolesAllowed(OperationRole.NAME)
    @RequestMapping(value = "/user/{id}/deleteRole", method = RequestMethod.DELETE, name = "{username} حذف نقش از کاربر")
    public void deleteRoleFromUser(@RequestParam String role, @PathVariable Long id) {
        userService.deleteFromRoleUser(role, id);

    }

    /*****************************************************************************

     Role

     *****************************************************************************/
    @RolesAllowed(AdministrationRole.NAME)
    @RequestMapping(value = "/role/{role}", method = RequestMethod.GET, name = "{role} دریافت اطلاعات نقش")
    public Role getRole(@PathVariable String role) {
        return accessService.getRole(role);
    }

    @RolesAllowed(AdministrationRole.NAME)
    @RequestMapping(value = "/role", method = RequestMethod.GET, name = "دریافت تمام نقش های موجود")
    public List<Role> getAllRoles() {
        return accessService.getAllRoles();
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/role", method = RequestMethod.POST, name = "ایجاد نقش")
    public void createRole(@RequestBody Role role) {
        accessService.createRole(role);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.DELETE, name = "{roleId} حذف نقش")
    public void deleteRole(@PathVariable String roleId) {
        accessService.deleteRole(roleId);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/role/{role}", method = RequestMethod.PUT, name = "{roleId} به روز رسانی مشخصات نقش")
    public void editCaptionOfRole(@PathVariable String role, @RequestParam String caption) {
        accessService.editCaptionOfRole(role, caption);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/role/{role}/assignPermission", method = RequestMethod.POST, name = "{role} دادن مجوز به نقش")
    public void assignPermissionTomRole(@PathVariable String role, @RequestParam Long permissionId) {
        accessService.assignPermissionToRole(role, permissionId);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/role/{roleId}/assignAllPermissions", method = RequestMethod.POST, name = "{roleId} دادن تمام مجوزهابه نقش")
    public void assignAllPermissionsToRole(@PathVariable String roleId) {
        accessService.assignAllPermissionsToRole(roleId);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/role/{role}/deletePermission", method = RequestMethod.DELETE, name = "{role} حذف مجوز از نقش")
    public void deletePermissionFromRole(@PathVariable String role, @RequestParam Long permissionId) {
        accessService.deleteFromPermissionRole(permissionId, role);
    }

    /*****************************************************************************

     Permission

     *****************************************************************************/
    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.GET, name = "{permissionId} دریافت اطلاعات مجوز")
    public Permission getPermission(@PathVariable Long permissionId) {
        return accessService.getPermission(permissionId);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/permission/{permissionId}", method = RequestMethod.PUT, name = "{permissionId} به روز رسانی مجوز")
    public void editCaptionOfPermission(@PathVariable Long permissionId, @RequestParam String caption) {
        accessService.editCaptionOfPermission(permissionId, caption);
    }

    @RolesAllowed(AdminRole.NAME)
    @RequestMapping(value = "/permission", method = RequestMethod.GET, name = "دریافت تمام مجوزهای موجود")
    public Page<Permission> getAllPermission(Pageable pageable) {
        return accessService.getAllPermission(pageable);
    }
}
