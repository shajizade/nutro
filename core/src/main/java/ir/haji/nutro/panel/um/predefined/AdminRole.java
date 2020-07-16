package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by ehejazi on 8/14/17.
 */
@Component
public class AdminRole extends Role {

    public static final String NAME = "ADMIN";
    public static final String CAPTION = "ادمین";

    public AdminRole() {
        super(NAME, CAPTION, new AdministrationRole());
    }

}
