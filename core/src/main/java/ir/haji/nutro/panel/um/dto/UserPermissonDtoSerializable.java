package ir.haji.nutro.panel.um.dto;


import java.io.Serializable;

/**
 * Created by Saeed on 2/14/17.
 */
public class UserPermissonDtoSerializable implements Serializable {
    private Long userId;
    private Long permission;

    public UserPermissonDtoSerializable() {
    }

    public UserPermissonDtoSerializable(Long userId, Long permission) {
        this.userId = userId;
        this.permission = permission;
    }
}
