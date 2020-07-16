package ir.haji.nutro.panel.um.entity;

import ir.haji.nutro.panel.um.dto.UserPermissonDtoSerializable;

import javax.persistence.*;

/**
 * Created by Saeed on 2/13/17.
 */

@Entity
@Table(name = "user_permission")
@IdClass(UserPermissonDtoSerializable.class)
public class UserPermission {
    @Id
    private Long userId;
    @JoinColumn(name = "permissionId")
    @Id
    private Permission permission;
    private Boolean denied;

    public UserPermission() {
    }

    public UserPermission(Long userId, Permission permission, Boolean denied) {
        this.userId = userId;
        this.permission = permission;
        this.denied = denied;
    }


    public Permission getPermission() {
        return permission;
    }

    public Boolean getDenied() {
        return denied;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }
}
