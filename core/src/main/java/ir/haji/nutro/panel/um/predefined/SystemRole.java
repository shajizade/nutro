package ir.haji.nutro.panel.um.predefined;

import ir.haji.nutro.panel.um.entity.Role;
import org.springframework.stereotype.Component;

/**
 * Created by ehejazi on 8/14/17.
 */
@Component
public class SystemRole extends Role {

    public static final String NAME = "SYSTEM";
    public static final String CAPTION = "سیستم";

    public SystemRole() {
        super(NAME, CAPTION, new AdminRole());
    }

}
