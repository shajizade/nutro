package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by ehejazi on 8/14/17.
 */
@Component
public class AdministrationRole extends Role {

    public static final String NAME = "ADMINISTRATION";
    public static final String CAPTION = "مدیریت";

    public AdministrationRole() {
        super(NAME, CAPTION, new BasicRole());
    }

}
