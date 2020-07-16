package ir.haji.nutro.panel.um.service;

import ir.haji.nutro.exception.BadRequestException;
import ir.haji.nutro.panel.um.conf.SecurityConf;
import ir.haji.nutro.panel.um.dto.UserAroundDto;
import ir.haji.nutro.panel.um.entity.*;
import ir.haji.nutro.panel.um.predefined.AdminRole;
import ir.haji.nutro.panel.um.predefined.AdministrationRole;
import ir.haji.nutro.panel.um.predefined.CustomerRole;
import ir.haji.nutro.panel.um.predefined.SystemRole;
import ir.haji.nutro.panel.um.repo.PermissionRepository;
import ir.haji.nutro.panel.um.repo.RoleRepository;
import ir.haji.nutro.panel.um.repo.UserPermissionRepository;
import ir.haji.nutro.panel.um.repo.UserRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by Saeed on 2/13/17.
 */
@Component
public class UserService implements UserDetailsManager {

    private static final String CODE_KEY = "code";
    private static final String VALIDATED_KEY = "validated";
    private static final String USERNAME_KEY = "username";
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PermissionRepository permissionRepository;
    @Autowired
    UserPermissionRepository userPermissionRepository;
    @Autowired
    AccessService accessService;
    @Autowired
    private MailService mailService;
    @Autowired
    private OrganizationService organizationService;
    @Value("${panel.adventureOrganizationId:8}")
    private Long adventureOrganizationId;

    public Boolean sendCode(String username, HttpSession session) {
        if (username == null) {
            User currentUser = getCurrentUser();
            if (currentUser == null)
                throw new BadRequestException("خطا");
            username = currentUser.getUsername();
        }
        String code = getCode();
        session.setAttribute(USERNAME_KEY, username);
        session.setAttribute(CODE_KEY, code);
        session.setAttribute(VALIDATED_KEY, false);
        return sendCode(username, code);
    }

    public Boolean sendCode(String username, String code) {
        mailService.sendCode(username, code);
        return true;
    }

    private String getCode() {
        Integer code = (int) (Math.random() * 100000 + 1);
        return code.toString();
    }

    @Override
    public void createUser(UserDetails user) {
        User dbUser = findByUsername(user.getUsername());
        if (dbUser != null)
            throw new BadRequestException("ایمیل یا شماره تلفن تکراری است.");
        persist((User) user);
    }

    public User getMe() {
        User user = getCurrentUser();
        if (user == null)
            throw new UsernameNotFoundException("کاربر یافت نشد");
        return user;
    }

    public User getUser(String username) {
        User user = findByUsername(username);
        if (user == null)
            throw new BadRequestException("کاربری با نام کاربری" + ":[" + username + "]" + " موجود نیست");
        return user;
    }

    public User createCustomer(User user, String password) {
        user = createUser(user, password);
        assignRoleToUser(CustomerRole.NAME, user);
        return user;
    }

    public User createUser(User user, String password) {
        if (user == null)
            throw new BadRequestException("کاربری وارد نشده است");
        if (user.getUsername() == null | user.getUsername().isEmpty())
            throw new BadRequestException("نام کاربری باید وارد شود");
        if (user.getName() == null)
            throw new BadRequestException("نام کاربر باید وارد شود");

        user.setEnable(true);
        setPassword(user, password);
        createUser((UserDetails) user);
        return user;
    }

    @Override
    public void updateUser(UserDetails user) {
        User thisUser = (User) user;
        User dbUser = findByUsername(user.getUsername());
        if (dbUser == null)
            throw new UsernameNotFoundException("کاربری یافت نشد");
        dbUser.setName(thisUser.getName());

        persist(dbUser);
    }

    public void persist(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(String username) {
        User dbUser = findByUsername(username);
        if (dbUser == null)
            throw new UsernameNotFoundException("کاربری یافت نشد");
        userRepository.delete(dbUser);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        User user = getCurrentUser();
        if (user == null || !checkPassword(user, oldPassword))
            throw new BadRequestException("پسورد قبلی صحیح نیست");
        if (oldPassword.equals(newPassword))
            throw new BadRequestException("پسورد جدید نباید تکراری باشد");
        setPassword(user, newPassword);
        persist(user);
    }

    @Override
    public boolean userExists(String username) {
        User user = findByUsername(username);
        if (user == null)
            return false;
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("");
        return user;

    }

    private String getEncryptedPassword(String password) {
        return SecurityConf.getPasswordEncoder().encode(password);
    }

    public Boolean checkPassword(User user, String password) {
        if (password == null || password.isEmpty())
            return false;
        if (user == null)
            return false;
        return passwordMatches(password, user.getPassword());
    }

    public User findByUsername(String username) throws UsernameNotFoundException {
        if (username == null || username.isEmpty())
            return null;
        User user = userRepository.findByPhone(username);
        if (user == null)
            user = userRepository.findByEmail(username);
        return user;
    }

    public User getCurrentUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getPrincipal() != null && authentication.getPrincipal() instanceof User)
                return (User) authentication.getPrincipal();

            return null;
        } catch (UsernameNotFoundException ignored) {
            return null;
        }
    }

    public void setPassword(User user, String password) {
        setPassword(user, password, false);
    }

    public void setPassword(User user, String password, Boolean expirable) {
        Date passwordExpired = new Date();
        user.setPassword(getEncryptedPassword(password));
        if (expirable) {
            passwordExpired = DateUtils.addHours(passwordExpired, 1);
            user.setPasswordExpired(passwordExpired);
        }
    }

    public void changeEnabled(String username, Boolean enabled) {
        User user = findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("کاربری یافت نشد");
        user.setEnable(enabled);
        persist(user);
    }

    public Page<User> getUsersList(UserSpec spec) {
        if (spec.getEmployee() != null && spec.getEmployee())
            spec.setOrganizationId(adventureOrganizationId);
        return userRepository.findAll(spec, spec.getPageable());
    }

    public void assignPermissionToUser(Long permissionId, String username, boolean denied) {
        Permission permission = accessService.getPermission(permissionId);
        User user = getUser(username);
        try {
            userPermissionRepository.save(new UserPermission(user.getId(), permission, denied));
        } catch (Exception exception) {
            throw new EmptyResultDataAccessException(0);
        }
    }

    public void deleteFromPermissionUser(Long permissionId, String username) {
        try {
            User user = findByUsername(username);
            Permission permission = permissionRepository.findById(permissionId).get();
            UserPermission userPermission = userPermissionRepository.findByUserIdAndPermission(user.getId(), permission);
            userPermissionRepository.delete(userPermission);
        } catch (Exception exception) {
            throw new EmptyResultDataAccessException(0);
        }
    }

    public void changeEnabledUser(Long id, Boolean enabled) {
        if (id == null)
            throw new BadRequestException("آی دی نباید بی مقدار باشد");
        User user = findByUserId(id);
        user.setEnable(enabled);
        persist(user);
    }

    public void resetPassword(Long id, String newPassword) {
        if (id == null)
            throw new UsernameNotFoundException("");
        User user = findByUserId(id);
        setPassword(user, newPassword);
        persist(user);
    }

    public void updateUser(Long userId, User user) {
        if (userId == null)
            throw new BadRequestException("نام کاربری باید مشخص باشد");
        User dbUser = findByUserId(userId);
        if (user.getName() != null)
            dbUser.setName(user.getName());
        if (user.getEmail() != null)
            dbUser.setEmail(user.getEmail());
        if (user.getPhone() != null)
            dbUser.setPhone(user.getPhone());
        persist(dbUser);
    }

    public User findByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent())
            return user.get();
        throw new BadRequestException("این کاربر یافت نشد");
    }

    public void assignRoleToUser(String role, Long userId) {
        assignRoleToUser(role, findByUserId(userId));
    }

    public void assignRoleToUser(String role, User user) {
        Role dbRole = accessService.getRole(role);
        if ((getCurrentUser() == null || !getCurrentUser().getRoles().contains(new AdminRole())) &&
                (dbRole.equals(new AdminRole()) || dbRole.equals(new SystemRole()))) {
            throw new BadRequestException("تنها یک ادمین می‌تواند یک ادمین جدید بسازد!");
        }
        if (user.getRoles().contains(dbRole))
            throw new BadRequestException("این نقش قبلا به کاربر داده شده است");
        user.getRoles().add(dbRole);
        userRepository.save(user);
    }

    public void deleteFromRoleUser(String role, Long id) {
        Role dbRole = accessService.getRole(role);
        User user = findByUserId(id);
        user.getRoles().remove(dbRole);
        userRepository.save(user);
    }

    public List<User> findByRole(Role role) {
        List<User> result = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            if (user.getRoles().contains(role))
                result.add(user);
        }
        return result;
    }

    private Boolean passwordMatches(String password, String ecryptedPassword) {
        return SecurityConf.getPasswordEncoder().matches(password, ecryptedPassword);
    }

    public void register(UserAroundDto userDto, HttpSession session) {
        if (getCurrentUser() != null)
            throw new BadRequestException("در حالت لاگین نمی‌توانید ثبت نام کنید!");
        Boolean validated = (Boolean) session.getAttribute(VALIDATED_KEY);
        if (validated != null && validated) {
            String username = (String) session.getAttribute(USERNAME_KEY);
            if (username.contains("@"))
                userDto.getUser().setEmail(username);
            else
                userDto.getUser().setPhone(username);
            userDto.getUser().setOrganization(organizationService.getOrganization(OrganizationService.websiteOrganizationId));
            User user = createUser(userDto.getUser(), userDto.getPassword());
            assignRoleToUser(CustomerRole.NAME, user);
            session.removeAttribute(VALIDATED_KEY);
            session.removeAttribute(CODE_KEY);
        } else {
            throw new BadRequestException("ابتدا باید ایمیل یا شماره تلفن خود را با دریافت کد، تایید کنید");
        }

    }

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return authentication.getName();
        }
        return null;
    }

    public boolean isCurrentUserAdministrator() {
        List<Role> roles = getCurrentUser().getRoles();
        for (Role role : roles) {
            if (role.equals(new AdministrationRole()))
                return true;
            if (role.hasParent(new AdministrationRole()))
                return true;
        }
        return false;
    }

    public void updateUserRoles(List<String> roles, Long id) {
        User user = findByUserId(id);

        for (String role : roles) {
            Role dbRole = accessService.getRole(role);
            if (!user.getRoles().contains(dbRole))
                user.getRoles().add(dbRole);
        }
        Iterator<Role> iterator = user.getRoles().iterator();
        while (iterator.hasNext()) {
            Role next = iterator.next();
            if (!roles.contains(next.getRole()))
                iterator.remove();
        }
        userRepository.save(user);
    }

    public void validate(String code, HttpSession session) {
        String sessionCode = (String) session.getAttribute(CODE_KEY);
        if (!code.equals(sessionCode)) {
            throw new BadRequestException("کد وارد شده صحیح نیست");
        }
        session.setAttribute(VALIDATED_KEY, true);
        session.removeAttribute(CODE_KEY);
    }

    public boolean isLoggeIn() {
        return getCurrentUser() != null;
    }

    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersByOrganizationId(Long organizationId) {
        return userRepository.findByOrgId(organizationId);
    }

    public Role getRoleByName(String roleName) {
        if (roleName == null)
            return null;
        return roleRepository.getRole(roleName);
    }
}
